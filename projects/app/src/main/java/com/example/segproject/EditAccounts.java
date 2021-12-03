package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditAccounts extends AppCompatActivity {
    // here admin will be able to delete accounts. customer and employee.
    List<User> customers;
    List<User> employees;

    ListView listViewCustomers;
    ListView listviewEmployees;

    DatabaseReference dbUsers;

    EditText editTextUsername;
    EditText editTextEmail;
    EditText editTextPassword;
    Button backButton;

    RadioButton radioNewRoleCustomer;
    RadioButton radioNewRoleEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_delete_account);


        listViewCustomers = (ListView) findViewById(R.id.CustomerListView);
        listviewEmployees = (ListView) findViewById(R.id.EmployeeListView);

        backButton = findViewById(R.id.adminEditAccountBackBTN);

        customers = new ArrayList<>();
        employees = new ArrayList<>();

        dbUsers = FirebaseDatabase.getInstance().getReference("users");


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAccounts.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        //listen for customer long press.
        listViewCustomers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                User customer = customers.get(position);

                deleteUserDialog(customer.getUserID(), customer.getUsername(), customer.getBranchID());
                return false;
            }
        });

        //listen for employee long press.
        listviewEmployees.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                User employee = employees.get(position);

                deleteUserDialog(employee.getUserID(), employee.getUsername(), employee.getBranchID());
                return false;
            }
        });
    }

    private void deleteUserDialog(final String id, String username, String branchID){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.delete_update_layout, null);
        dialogBuilder.setView(dialogView);

        editTextUsername = (EditText) dialogView.findViewById(R.id.usernameUpdateEditText);
        editTextEmail  = (EditText) dialogView.findViewById(R.id.emailUpdateEditText);
        editTextPassword = (EditText) dialogView.findViewById(R.id.passwordUpdateEditText);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.updateServiceButton);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.deleteUserButton);

        dialogBuilder.setTitle(username);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                radioNewRoleCustomer = (RadioButton) dialogView.findViewById(R.id.updateCustomerRoleRadioButton);
                radioNewRoleEmployee = (RadioButton) dialogView.findViewById(R.id.updateEmployerRoleRadioButton);

                if (validateUpdateInput(username, email, password)) {
                    if (radioNewRoleCustomer.isChecked()){
                        updateUser(id, username, email, password, "Customer", branchID);
                        b.dismiss();
                    }else if (radioNewRoleEmployee.isChecked()){
                        updateUser(id, username, email, password, "Employee", branchID);
                        b.dismiss();
                    }
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(id);
                b.dismiss();
            }
        });
    }

    protected void onStart(){//have list of all customers and employees
        super.onStart();
        dbUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                customers.clear();
                employees.clear();

                for(DataSnapshot info : snapshot.getChildren()){
                    User user = info.getValue(User.class);
                    if (user.getRole().equals("Customer")){
                        customers.add(user);
                    }else if (user.getRole().equals("Employee")){
                        employees.add(user);
                    }
                }

                UserList customerAdapter = new UserList(EditAccounts.this, customers);
                UserList employeeAdapter = new UserList(EditAccounts.this, employees);

                listViewCustomers.setAdapter(customerAdapter);
                listviewEmployees.setAdapter(employeeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void deleteUser(String id){ //this does not delete user from authenticated database. but does delete from realtime.
        dbUsers.child(id).removeValue();
        Toast.makeText(getApplicationContext(), "User deleted", Toast.LENGTH_LONG).show();

    }

    public void updateUser(String id, String username, String email, String password, String role, String branchID){

        User user = new User(username, email, password, role, id, branchID);
        dbUsers.child(id).setValue(user);
        Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_LONG).show();
    }

    public Boolean validateUpdateInput(String username,String email, String password) { // method to validate sign up info

        if (username.isEmpty()) {
            editTextUsername.setError("Please enter a username");
            editTextUsername.requestFocus();
            return false;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Please enter an Email");
            editTextEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid Email");
            editTextEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Please enter a password");
            editTextPassword.requestFocus();
            return false;
        }

        if (password.length() < 6){
            editTextPassword.setError("Password must be at least 6 characters long");
            editTextPassword.requestFocus();
            return false;
        }

        if (!(radioNewRoleCustomer.isChecked()) && !(radioNewRoleEmployee.isChecked())){
            Toast.makeText(getApplicationContext(), "Please select a role", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true; // if we reach here that means that the input is fine.
    }


}
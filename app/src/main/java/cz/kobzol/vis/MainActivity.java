package cz.kobzol.vis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cz.kobzol.vis.dao.PersonDTO;
import cz.kobzol.vis.net.NetworkCommunicator;
import cz.kobzol.vis.net.ServiceResponse;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        NetworkCommunicator.initInstance(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submitButton = (Button) this.findViewById(R.id.login_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) MainActivity.this.findViewById(R.id.login_username)).getText().toString();
                String password = ((EditText) MainActivity.this.findViewById(R.id.login_password)).getText().toString();

                NetworkCommunicator.getInstance().isLoginValid(username, password, new ServiceResponse<Boolean>() {
                    @Override
                    public void onResult(Boolean result) {
                        if (!result) {
                            Toast.makeText(MainActivity.this, "The username/password combination is not valid", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(MainActivity.this, GradesActivity.class);
                            MainActivity.this.startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(Throwable exception) {
                        Toast.makeText(MainActivity.this, "An error occured: " + exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}

package edu.whu.shellshock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends Activity {
	private LayoutInflater mLayoutInflater;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutInflater = LayoutInflater.from(this);
        setContentView(R.layout.activity_main);
        ImageView logImage = (ImageView) findViewById(R.id.login);
        logImage.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				final View loginView = mLayoutInflater.inflate(R.layout.login, null);
				AlertDialog loginDia = new AlertDialog.Builder(MainActivity.this)
					.setMessage("Input your information")
					.setTitle("Log in")
					.setView(loginView)
					.setPositiveButton("Log in", new DialogInterface.OnClickListener() {				
						@Override
						public void onClick(DialogInterface dialog, int which) {
							startActivity(new Intent(MainActivity.this, MenuActivity.class));
						}
					})
					.setNegativeButton("Log up", new DialogInterface.OnClickListener() {			
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(MainActivity.this, "Log up", 10).show();
							
						}
					}).create(); 
				loginDia.show();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

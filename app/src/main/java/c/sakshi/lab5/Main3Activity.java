package c.sakshi.lab5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {

    public int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //1. Get editText view and the content that user entered.
        EditText editTextView = (EditText) findViewById(R.id.editTextView);
        String content = editTextView.getText().toString();
        //2. Get Intent.
        Intent intent = getIntent();
        String str = intent.getStringExtra("noteid");
        //3. Get the value of integer "noteid" from intent.
        //4. Initialise class variable "noteid" with the value from intent.
        if (str != null) {
            noteid = Integer.parseInt(str);
        }
        String tag = "lab5";
        Log.i(tag,"NoteID: " + str);

        if (noteid != -1) {
            //Display content of note by retrieving "notes" ArrayList in Main2Activity.
            Note note = Main2Activity.notes.get(noteid);
            String noteContent = note.getContent();

            //Use editText.setText() to display the contents of this note on screen.
            editTextView.setText(noteContent);
        }

    }

    public void onClick(View view){
        //Implements SAVE button
        //Adds note to SQLite database
        saveMethod(view);

    }

    public void saveMethod(View view){
        //1. Get editText view and the content that user entered.
        EditText editTextView = (EditText) findViewById(R.id.editTextView);
        String content = editTextView.getText().toString();
        //2. Initialise SQLiteDatabase instance.
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",
                Context.MODE_PRIVATE, null);
        //3. Initialise DBHelper class.
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        //4. Set username in the following variable by fetching it from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(MainActivity.usernameKey, "");
        //5. Save information to database
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) { //Add note.
            title = "Note " + (Main2Activity.notes.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        } else { //Update note.
            title = "Note " + (noteid + 1);
            dbHelper.updateNote(title, date, content, username);
        }

        //6. Go to second activity using intents.
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("message", username);
        startActivity(intent);
    }


}

package c.sakshi.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    TextView textView2;

    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //1. Display welcome message. Fetch username from SharedPreferences.

        textView2 = (TextView) findViewById(R.id.textView2);
        Intent intent1 = getIntent();
        String user = intent1.getStringExtra("message");
        String str = "Welcome " + user + "!";
        textView2.setText(str);

        //2. Get SQLiteDatabase instance.
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",
                Context.MODE_PRIVATE, null);
        //3. Initiate the "notes" class variable using readNotes method implemented in DBHelper class.
        //      got from SharedPreferences as a parameter to readNotes method.
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        notes = dbHelper.readNotes(user);
        //4. Create an ArrayList<String> object by iterating over notes object.

        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes){
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        //5. Use ListView view to display notes on screen.
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

        //6. Add onItemClickListener for ListView item, a note in our case.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //Initialise intent to take user to third activity (NoteActivity in this case).
                Intent intent2 = new Intent(getApplicationContext(), Main3Activity.class);
                //Add the position of the item that was clicked on as "noteid"
                String pos = new Integer(position).toString();
                intent2.putExtra("noteid", pos);
                startActivity(intent2);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.item1:
                //Left for milestone 2
                Intent intent1 = new Intent(this, Main3Activity.class);

                startActivity(intent1);
                return true;
            case R.id.item2:
                Intent intent2 = new Intent(this, MainActivity.class);

                SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
                sharedPreferences.edit().remove(MainActivity.usernameKey).apply();

                startActivity(intent2);
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }
}

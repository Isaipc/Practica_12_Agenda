package com.app.practica_12_agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    EditText et_content;
    EditText et_date;


    public static final String FILE_NAME = "notas.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        et_date  = findViewById(R.id.et_date);
        et_content = findViewById(R.id.et_content);
        String[] files = fileList();
        Log.w("TAG", Arrays.toString(files));

    }

    public void save(View view)
    {
        String[] files = fileList();
        Log.w("TAG", Arrays.toString(files));
        try {
            OutputStreamWriter writer  =
                    new OutputStreamWriter(openFileOutput(
                            et_date.getText().toString().replace("/","_"),
                            Activity.MODE_PRIVATE));
            writer.write(et_content.getText().toString());
            Toast.makeText(getApplicationContext(), "Archivo guardado!", Toast.LENGTH_SHORT).show();
            writer.flush();
            writer.close();

            clear();

            Log.w("TAG", Arrays.toString(files));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void recover(View view )
    {
        String[] files = fileList();
        Log.w("TAG", Arrays.toString(files));
        String fileName = et_date.getText().toString().replace("/","_");

        try {
            if(exists(files, fileName))
            {
                InputStreamReader isr =
                        new InputStreamReader(
                                openFileInput(fileName));
                BufferedReader br = new BufferedReader(isr);

                String line = br.readLine();
                StringBuilder str = new StringBuilder();
                while   (line != null)
                {
                    str.append(line);
                    line = br.readLine();
                }

                et_content.setText(str.toString());
                Toast.makeText(getApplicationContext(), "Archivo encontrado!", Toast.LENGTH_SHORT).show();
                br.close();
                isr.close();
            }
            else
                Toast.makeText(getApplicationContext(), "El archivo no existe", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean exists(String[] files, String fileName)
    {
        for(String f : files)
            if(fileName.equals(f))
                return true;
        return false;
    }

    private void clear()
    {
        et_content.setText("");
        et_date.setText("");
    }
}

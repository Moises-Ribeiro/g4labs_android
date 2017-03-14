package g4.com.br.devandroid1;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView2;
    private Button button1;
    private Button button3;
    private Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView2 = (TextView) findViewById(R.id.textView2);
        button1 = (Button) findViewById(R.id.button);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        button1.setOnClickListener(this);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button2Click(v);
            }
        });

        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v instanceof Button) {
            String text = ((Button) v).getText().toString();
            textView2.setText(text);


            if ("BANCO DE DADOS".equals(text)) {
                Intent intent = new Intent(this, BancoDadosActivity.class);
                startActivity(intent);

            } else if ("CACHE".equals(text)) {
                Intent intent = new Intent(this, CacheActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public void button2Click(View v) {
        this.onClick(v);
    }
}

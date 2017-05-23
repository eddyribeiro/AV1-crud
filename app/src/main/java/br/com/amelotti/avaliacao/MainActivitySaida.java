package br.com.amelotti.avaliacao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;




public class MainActivitySaida extends AppCompatActivity {

    EditText editRA,editNome,editAV1,editAv2;
    EditText editMedia;
    Float media;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saida);

        editRA = (EditText) findViewById(R.id.editRA);
        editNome = (EditText) findViewById(R.id.editNome);
        editAV1 = (EditText) findViewById(R.id.editAV1);
        editAv2 = (EditText) findViewById(R.id.editAV2);
         editMedia = (EditText) findViewById(R.id.editMedia);

        Intent it = getIntent();

        if (it!=null){

            Bundle params = it.getExtras();

            if (params!=null){

                String strNome = params.getString("nome");
                String strRA = params.getString("ra");
                String strAv1 = params.getString("av1");
                String strAv2 = params.getString("av2");

                editRA.setText(strRA.toString());
                editNome.setText(strNome.toString());
                editAV1.setText(strAv1.toString());
                editAv2.setText(strAv2.toString());


                media = Float.parseFloat(strAv1) + Float.parseFloat(strAv2);

                media = media / 2;

                editMedia.setText("Media do aluno é: " + media);



            }



        }






    }

}

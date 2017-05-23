package br.com.amelotti.avaliacao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;


public class MainActivity extends Activity implements OnClickListener  {
    EditText editRa, editNome, editAV1,editAV2;
    Button btnAdd, btnDelete, btnModify, btnView, btnViewAll,btnAverage;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editRa = (EditText) findViewById(R.id.editRA);
        editNome = (EditText) findViewById(R.id.editNome);
        editAV1 = (EditText) findViewById(R.id.editAV1);
        editAV2 = (EditText) findViewById(R.id.editAV2);

        btnAdd = (Button) findViewById(R.id.btAdicionar);
        btnDelete = (Button) findViewById(R.id.btExcluir);
        btnModify = (Button) findViewById(R.id.btAlterar);
        btnView = (Button) findViewById(R.id.btnPesquisar);
        btnViewAll = (Button) findViewById(R.id.btListar);
        btnAverage = (Button) findViewById(R.id.btMedia);

        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnViewAll.setOnClickListener(this);
        btnAverage.setOnClickListener(this);
        db = openOrCreateDatabase("AlunoDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS aluno(ra VARCHAR, nome VARCHAR, av1 REAL, av2 REAL);");





    }
    @Override
    public void onClick(View view) {

        if (view == btnAdd) {
            if (editRa.getText().toString().trim().length() == 0 || editNome.getText().toString().trim().length() == 0
                    || editAV1.getText().toString().trim().length() == 0 || editAV2.getText().toString().trim().length() == 0 ) {
                showMessage("Erro", "Entre com valores válidos!");
                return;
            }
            db.execSQL("INSERT INTO aluno VALUES('" + editRa.getText() + "','" + editNome.getText() + "','"
                    + editAV1.getText() + "','" + editAV2.getText() + "');");
            showMessage("Successo", "Aluno Incluído!");
            clearText();
        }

        if (view == btnDelete) {
            if (editRa.getText().toString().trim().length() == 0) {
                showMessage("Error", "Digite o RA!");
                return;
            }
            Cursor c = db.rawQuery("SELECT * FROM aluno WHERE ra='" + editRa.getText() + "'", null);
            if (c.moveToFirst()) {
                db.execSQL("DELETE FROM aluno WHERE ra='" + editRa.getText() + "'");
                showMessage("Successo", "Registro Excluído!");
            } else {
                showMessage("Error", "RA Inválido!");
            }
            clearText();
        }

        if (view == btnModify) {
            if (editRa.getText().toString().trim().length() == 0) {
                showMessage("Erro", "Informe o RA!");
                return;
            }
            Cursor c = db.rawQuery("SELECT * FROM aluno WHERE ra='" + editRa.getText() + "'", null);
            if (c.moveToFirst()) {
                db.execSQL("UPDATE aluno SET nome='" + editNome.getText() + "',av1='" + editAV1.getText()
                        + "',av2='" + editAV2.getText() + "' WHERE ra='" + editRa.getText() + "'");
                showMessage("Successo", "Aluno Alterado com Sucesso!");
            } else {
                showMessage("Erro", "RA Inválido!");
            }
            clearText();
        }

        if (view == btnView) {
            if (editRa.getText().toString().trim().length() == 0) {
                showMessage("Erro", "Informe o RA!");
                return;
            }
            Cursor c = db.rawQuery("SELECT * FROM aluno WHERE ra='" + editRa.getText() + "'", null);
            if (c.moveToFirst()) {
                editNome.setText(c.getString(1));
                editAV1.setText(c.getString(2));
                editAV2.setText(c.getString(3));
            } else {
                showMessage("Erro", "RA Inválido!");
                clearText();
            }
        }
//Aqui a média

        if (view == btnAverage) {
            if (editRa.getText().toString().trim().length() == 0) {
                showMessage("Erro", "Informe o RA!");
                return;
            }





            Cursor c = db.rawQuery("SELECT * FROM aluno WHERE ra='" + editRa.getText() + "'", null);

            if (c.moveToFirst()) {
                editRa.setText(c.getString(0));
                editNome.setText(c.getString(1));
                editAV1.setText(c.getString(2));
                editAV2.setText(c.getString(3));

                Intent it = new Intent(this, MainActivitySaida.class);
                Bundle params = new Bundle();

                params.putString("ra", editRa.getText().toString());
                params.putString("nome", editNome.getText().toString());
                params.putString("av1", editAV1.getText().toString());
                params.putString("av2", editAV2.getText().toString());
                it.putExtras(params);
                startActivity(it);




            } else {
                showMessage("Erro", "RA Inválido!");
                clearText();
            }
        }





        if (view == btnViewAll) {
            Cursor c = db.rawQuery("SELECT * FROM aluno", null);
            if (c.getCount() == 0) {
                showMessage("Erro", "Nenhum Registro Encontrado!");
                return;
            }
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext()) {
                buffer.append("RA: " + c.getString(0) + "\n");
                buffer.append("Nome: " + c.getString(1) + "\n");
                buffer.append("AV1: " + c.getString(2) + "\n\n");
                buffer.append("AV2: " + c.getString(3) + "\n\n");
            }
            showMessage("Detalhes:", buffer.toString());
        }

    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText() {
        editRa.setText("");
        editNome.setText("");
        editAV1.setText("");
        editAV2.setText("");
        editRa.requestFocus();
    }



}





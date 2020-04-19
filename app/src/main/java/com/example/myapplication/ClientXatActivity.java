package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Client en Androdi que funciona com a client d'un Xat.
 * Si es fa servir l'emulador cal fer
 * posar primer en marxa el emulador i després
 * telnet localhost 5554
 * auth
 * redir add tcp:5050:8189
 * <p>
 * telnet localhost 8189
 *
 * @author sergi.grau@fje.edu
 * @version 1.0 6.4.2020
 * @format UTF-8
 * <p>
 * NOTES
 * ORIGEN
 * Exercici demostratiu de comunicacions amb socols i fils
 */
public class ClientXatActivity extends AppCompatActivity {

    private Button boto;
    private EditText entrada;
    private PrintWriter sortida;
    private Socket socol = null;

    private ListView listMessages;
    private ArrayList<String> arrayMensajes;
    private ArrayAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boto = findViewById(R.id.boto);
        entrada = findViewById(R.id.entrada);


        //cosas ivan
        this.arrayMensajes = new ArrayList<>();
        this.listMessages = (ListView) findViewById(R.id.mensajesListView);
        this.listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayMensajes);

        byte[] addr = new byte[4];
        addr[0] = (byte) 192;
        addr[1] = (byte) 168;
        addr[2] = (byte) 1;
        addr[3] = (byte) 33;
        try {
            InetAddress adreca = InetAddress.getByAddress(addr);
            socol = new Socket();
            try {
                socol.connect(new InetSocketAddress(adreca.getHostAddress(),
                        8189), 2000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            LecturaFil lectura = new LecturaFil(socol);
            lectura.start();


            boto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        OutputStream outStream = socol.getOutputStream();
                        PrintWriter sortida = new PrintWriter(outStream, true);
                        sortida.println(entrada.getText().toString());


                    } catch (UnknownHostException e) {
                        System.out.println("host desconegut");
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.out.println("problemes E/S");
                    }
                }
            });
        } catch (UnknownHostException ex) {
            System.out.println("problemes amb el servidor");
        } catch (IOException e) {
            System.out.println("problemes amb la connexio");
        }

    }

    public void actualizarXat(View v){
        listMessages.setAdapter(listAdapter);
        System.out.println(arrayMensajes);
    }


    class LecturaFil extends Thread {
        private Socket socol = null;

        public LecturaFil(Socket s) {
            socol = s;

        }

        public void run() {

            try {

                InputStream inStream = socol.getInputStream();
                Scanner entrada = new Scanner(inStream);
                while (true) {
                    String resposta = entrada.nextLine();
                    arrayMensajes.add(resposta);
                    System.out.println("\nSERVER> " + resposta);


                }

            } catch (UnknownHostException e) {
                System.out.println("host desconegut");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("problemes E/S");
            } catch (Exception e) {

            }

        }

    }
}



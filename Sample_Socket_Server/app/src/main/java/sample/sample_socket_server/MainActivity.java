package sample.sample_socket_server;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et;
                et = (EditText)findViewById(R.id.editText);

                AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

                    @Override
                    protected String doInBackground(Void... voids) {

                        // データ受信準備
                        String receiveString = "NG";
                        ServerSocket serverSocket = null;
                        BufferedReader reader = null;

                        // データ受信
                        try {
                            serverSocket = new ServerSocket(5000);

                            // ここでデータを受信するまで待機
                            Socket socket = serverSocket.accept();

                            // 受信したデータを格納
                            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            receiveString = reader.readLine();

                            reader.close();
                            socket.close();
                            serverSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return receiveString;
                    }

                    @Override
                    protected void onPostExecute(String string) {

                        // 受信したデータを表示。
                        et.setText(string);
                    }
                };
                task.execute();
            }
        });
    }
}

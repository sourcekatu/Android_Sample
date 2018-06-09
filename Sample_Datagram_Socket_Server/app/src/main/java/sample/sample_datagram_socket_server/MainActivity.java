package sample.sample_datagram_socket_server;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.CountDownLatch;

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

                AsyncTask<Void, Void, DatagramPacket> task = new AsyncTask<Void, Void, DatagramPacket>() {

                    @Override
                    protected DatagramPacket doInBackground(Void... voids) {

                        // データ受信準備
                        byte receiveBuff[] = new byte[1000];
                        DatagramSocket datagramSocket = null;
                        DatagramPacket datagramPacket = new DatagramPacket(receiveBuff, receiveBuff.length);

                        // データ受信
                        try {
                            datagramSocket = new DatagramSocket(5000);
                            datagramSocket.receive(datagramPacket);
                            datagramSocket.close();
                        } catch (SocketException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return datagramPacket;
                    }

                    @Override
                    protected void onPostExecute(DatagramPacket datagramPacket1) {
                        String string = "NG";

                        // 受信したデータはbyte配列なので、文字列に変換。
                        try {
                            string = new String(datagramPacket1.getData(), "SHIFT_JIS");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        // 受信したデータを表示。
                        et.setText(string);
                    }
                };
                task.execute();
            }
        });
    }
}

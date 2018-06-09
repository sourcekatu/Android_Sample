package sample.sample_datagram_socket_client;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class MainActivity extends AppCompatActivity {

    Spinner spinner1;   // IPアドレス1
    Spinner spinner2;   // IPアドレス2
    Spinner spinner3;   // IPアドレス3
    Spinner spinner4;   // IPアドレス4
    EditText et;         // 送信テキスト

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner1 = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);

        // IPアドレス設定 0 ～ 255 Start-----------------------------------
        String items[] = new String[256];
        int ii;

        for (ii = 0; ii < 256; ii++)
        {
            items[ii] = String.valueOf(ii);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner4.setAdapter(adapter);
        // IPアドレス設定 0 ～ 255 End------------------------------------

        // IPアドレスの初期値設定 192.168.0.0
        spinner1.setSelection(192);
        spinner2.setSelection(168);
        spinner3.setSelection(0);
        spinner4.setSelection(0);

        Button button = (Button) findViewById(R.id.button);

        // 送信ボタン押下処理
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String IPAddress = String.valueOf(spinner1.getSelectedItem()) + '.' +
                                   String.valueOf(spinner2.getSelectedItem()) + '.' +
                                   String.valueOf(spinner3.getSelectedItem()) + '.' +
                                   String.valueOf(spinner4.getSelectedItem());

                et = (EditText)findViewById(R.id.editText);
                byte buf[] = new byte[et.length()];

                String string = et.getText().toString();

                // 日本語を通信できるようにする。
                try {
                    buf = string.getBytes("SHIFT_JIS");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                // ソケット通信用にポート設定。送信したいデータとIPアドレス設定。
                InetSocketAddress inetSocketAddress = new InetSocketAddress(IPAddress, 5000);
                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, inetSocketAddress);

                // Androidではソケット通信は別スレッドにする必要がある。ここで非同期通信。
                AsyncTask<DatagramPacket, Void, Void> task = new AsyncTask<DatagramPacket, Void, Void>() {
                    @Override
                    protected Void doInBackground(DatagramPacket... datagramPackets) {
                        DatagramSocket datagramSocket = null;
                        try {
                            datagramSocket = new DatagramSocket();
                            datagramSocket.send(datagramPackets[0]);
                            datagramSocket.close();
                        } catch (SocketException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                };
                task.execute(datagramPacket);
            }
        });
    }
}

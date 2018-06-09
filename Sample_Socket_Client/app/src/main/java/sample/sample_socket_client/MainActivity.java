package sample.sample_socket_client;

import android.os.AsyncTask;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
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

                // データの送信はbyte配列方になるので変換
                for (int ii = 0; ii < et.length(); ii++ )
                {
                    buf[ii] = (byte) et.getText().charAt(ii);
                }

                // ソケット通信用にポート設定。送信したいデータとIPアドレス設定。
                InetSocketAddress inetSocketAddress = new InetSocketAddress(IPAddress, 5000);

                // Androidではソケット通信は別スレッドにする必要がある。ここで非同期通信。
                AsyncTask<InetSocketAddress, Void, Void> task = new AsyncTask<InetSocketAddress, Void, Void>() {
                    @Override
                    protected Void doInBackground(InetSocketAddress... inetSocketAddresses) {
                        Socket socket = null;
                        try {
                            // 接続
                            socket = new Socket();
                            socket.connect(inetSocketAddresses[0]);

                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                            // データを送信。
                            writer.write(et.getText().toString());

                            // クローズしておかないと、受信側でデータが反映されなかった。多分、クローズしたときに受信が側にデータが反映される。
                            writer.close();

                            socket.close();
                        } catch (SocketException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                };
                task.execute(inetSocketAddress);
            }
        });
    }
}

package sample.sample_datagram_socket_server_repeat;

import android.os.AsyncTask;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

/**
 * Created by Kazuki on 2018/06/16.
 */

public class ReceiveASyncTask extends AsyncTask<DatagramPacket, Void, DatagramPacket> {

    private EditText mew;
    DatagramPacket datagramPacket;
    byte receiveBuff[] = new byte[1000];
    private CallBackTask mcallbacktask;

    public ReceiveASyncTask(EditText ew)
    {
        mew = ew;
    }

    // バックグラウンドの処理。ここではTextViewの操作はできない。
    @Override
    protected DatagramPacket doInBackground(DatagramPacket... datagramPackets) {
        return datagramPackets[0];
    }

    // doInBackgroundの処理が終了した後に呼ばれる。ここではTextViewの操作はできる。
    @Override
    protected void onPostExecute(DatagramPacket datagramPacket1) {

        String string = "NG";

        // 受信したデータはbyte配列なので、文字列に変換。
        try {
            string = new String(datagramPacket1.getData(), "SHIFT_JIS");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        mew.setText(string);

        mcallbacktask.CallBack();
    }

    public void setOnCallBack(CallBackTask t_object)
    {
        mcallbacktask = t_object;
    }

    // コールバック用のインターフェース定義
    interface CallBackTask
    {
        void CallBack();
    }
}

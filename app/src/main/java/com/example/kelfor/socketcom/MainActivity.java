package com.example.kelfor.socketcom;


//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.net.Socket;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//public class MainActivity extends Activity implements Runnable {
//    private TextView tv_msg = null;
//    private EditText ed_msg = null;
//    private Button btn_send = null;
//    // private Button btn_login = null;
//    private static final String HOST = "10.0.2.2";
//    private static final int PORT = 9999;
//    private Socket socket = null;
//    private BufferedReader in = null;
//    private PrintWriter out = null;
//    private String content = "";
//接收线程发送过来信息，并用TextView显示
//    public Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            tv_msg.setText(content);
//        }
//    };

//   @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        tv_msg = (TextView) findViewById(R.id.textView);
//        ed_msg = (EditText) findViewById(R.id.edit);
//        btn_send = (Button) findViewById(R.id.btnSend);
//
//        try {
//            socket = new Socket(HOST, PORT);
//            in = new BufferedReader(new InputStreamReader(socket
//                    .getInputStream()));
//            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
//                    socket.getOutputStream())), true);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            ShowDialog("login exception" + ex.getMessage());
//        }
//        btn_send.setOnClickListener(new Button.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                String msg = ed_msg.getText().toString();
//                if (socket.isConnected()) {
//                    if (!socket.isOutputShutdown()) {
//                        out.println(msg);
//                    }
//                }
//            }
//        });
//        //启动线程，接收服务器发送过来的数据
//        new Thread(MainActivity.this).start();
//    }
//    /**
//     * 如果连接出现异常，弹出AlertDialog！
//     */
//    public void ShowDialog(String msg) {
//        new AlertDialog.Builder(this).setTitle("notification").setMessage(msg)
//                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                }).show();
//    }
//    /**
//     * 读取服务器发来的信息，并通过Handler发给UI线程
//     */
//    public void run() {
//        try {
//            while (true) {
//                if (!socket.isClosed()) {
//                    if (socket.isConnected()) {
//                        if (!socket.isInputShutdown()) {
//                            if ((content = in.readLine()) != null) {
//                                content += "\n";
//                                mHandler.sendMessage(mHandler.obtainMessage());
//                            } else {
//
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText sendMsg;
    private PrintWriter out;
    private BufferedReader br;
    private Button btnSend,btnConnect;
    private Socket socket=null;
    private TextView textView = null;
    private Handler handler = null;
    private EditText serverIP = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendMsg = (EditText)findViewById(R.id.edit_sendMsg);
        sendMsg.setText("发至服务器的数据：");
        btnSend = (Button)findViewById(R.id.btnSend);
        btnConnect = (Button)findViewById(  R.id.btnConnect);

        textView = (TextView)findViewById(R.id.textView);
        textView.setText("来自服务器的数据：");

        serverIP = (EditText)findViewById(R.id.server);

        handler = new Handler()//Use this to update the textView!!!
        {
            @Override
            public void handleMessage(Message msg)
            {
              //  showToast(msg.obj.toString());
                textView.setText(msg.obj.toString());
            }
        };
        //  new Thread(MainActivity.this).start();

        btnSend.setOnClickListener(this);
        btnConnect.setOnClickListener(this);
    }

    private Thread tcpThread = new Thread()
    {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {

                //建立连接到远程服务器的Socket
//            socket = new Socket("100.64.148.130",9527);
//            out = new PrintWriter( socket.getOutputStream());
//            //将Socket对应的输入流包装成BufferedReader
//             br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                try {
                    while (true) {

                        String str = br.readLine();

                    //    char [] strl
                     //   br.read()
                        if(str!=null)
                        {
                            showToast(str);
                            Message msg = new Message();
                            msg.obj  = str;
                            msg.what = 0x88;
                            handler.sendMessage(msg);

                        }



                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };
    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnConnect:
            {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String ip = serverIP.getText().toString().trim();

                            showToast("连接成功");
                            socket = new Socket(ip,9527);
                            out = new PrintWriter( socket.getOutputStream());
                            //将Socket对应的输入流包装成BufferedReader
                            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                            tcpThread.start();

                        } catch (Exception e) {
                        }
                    }
                }).start();
                break;
            }
            case R.id.btnSend:
            {
                String msg = sendMsg.getText().toString();
                out.print(msg);
                out.flush();//Very Important!
                break;
            }
            default:break;
        }

    }

//
//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            // Inflate the menu; this adds items to the action bar if it is present.
//            getMenuInflater().inflate(R.menu.main, menu);
//            return true;
//        }

//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//            // Handle action bar item clicks here. The action bar will
//            // automatically handle clicks on the Home/Up button, so long
//            // as you specify a parent activity in AndroidManifest.xml.
//            int id = item.getItemId();
//            if (id == R.id.action_settings) {
//                return true;
//            }
//            return super.onOptionsItemSelected(item);
//        }
}
package com.example.lab3_1;

import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.lab3_1.MakeThings;
import com.example.lab3_1.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MakeThings extends ActionBarActivity {
	 private SocketIO socket;
	 public int[] data = new int[20];
	 GLSurfaceView surfacex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupSocket();
        setContentView(R.layout.activity_make_things);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    
        
    }

    private void setupSocket(){
    	try{
    		socket = new SocketIO("http://192.168.2.4:8080/");
    		
    		socket.connect(new IOCallback() {
                @Override
                public void onMessage(JSONObject json, IOAcknowledge ack) {
                    try {
                        System.out.println("Server said:" + json.toString(2));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                
                @Override
                public void onMessage(String data, IOAcknowledge ack) {
                    System.out.println("Server said: " + data);
                }

                @Override
                public void onError(SocketIOException socketIOException) {
                    System.out.println("an Error occured");
                    socketIOException.printStackTrace();
                }

                @Override
                public void onDisconnect() {
                    System.out.println("Connection terminated.");
                }

                @Override
                public void onConnect() {
                    System.out.println("Connection established");
                }
                
                
                @Override
                public void on(String event, IOAcknowledge ack, Object... args) {
                	int count = 0;
                    System.out.println("Server triggered event '" + event + "'");
                    System.out.println("Data: " + args[0].toString());
                    data[count] = Integer.parseInt(args[0].toString());
                    count++;
                }
            });

            // This line is cached until the connection is establisched.
            socket.send("Hello Server!");
    		
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
          
    
    }
    
public void button1(View v) throws MalformedURLException, Exception{
	
    	socket.emit("temp", "1");
        
		// int example series data
		GraphViewSeries plot1 = new GraphViewSeries(new GraphViewData[] {
		    new GraphViewData(1, data[0])
		    , new GraphViewData(2, data[1])
		    , new GraphViewData(3, data[2])
		    , new GraphViewData(4, data[3])
		    , new GraphViewData(5, data[4])
		    , new GraphViewData(6, data[5])
		    , new GraphViewData(7, data[6])
		    , new GraphViewData(8, data[7])
		    , new GraphViewData(9, data[8])
		    , new GraphViewData(10, data[9])
		    , new GraphViewData(11, data[10])
		    , new GraphViewData(12, data[11])
		    , new GraphViewData(13, data[12])
		    , new GraphViewData(14, data[13])
		    , new GraphViewData(15, data[14])
		    , new GraphViewData(16, data[15])
		    , new GraphViewData(17, data[16])
		    , new GraphViewData(18, data[17])
		    , new GraphViewData(19, data[18])
		    , new GraphViewData(20, data[19])
		});
		 
		GraphView graphView = new LineGraphView(
		    MakeThings.this // context
		    , "2DPlot" // heading
		);
		graphView.addSeries(plot1); // data
		 
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
		layout.addView(graphView);
    }

public class GraphViewData implements GraphViewDataInterface {
    private double x,y;

    public GraphViewData(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }
}

    public void button3D (View v){
    	surfacex = new GLSurfaceView(this);
    	surfacex.setRenderer( new OpenRenderer());
    	setContentView(surfacex);
    }
  //  protected void onPause(){
    //	super.onPause();
    //	surfacex.onPause();
   // }
   // protected void onResume(){
    //	super.onResume();
    //	surfacex.onResume();
   //}
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.make_things, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_make_things, container, false);
            return rootView;
        }
    }

}

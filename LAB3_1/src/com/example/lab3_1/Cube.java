package com.example.lab3_1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Cube {
private float vertices[] = {
		1, 1, -1,
		1, -1, -1,
		-1, -1, -1,
		-1, 1, -1,
		1, 1, 1,
		1, -1, 1,
		-1, -1, 1,
		-1, 1, 1,
};
private short[] pIndex = {
		3,4,0, 0,4,1, 3,0,1,
		3,7,4, 7,6,4, 7,3,6,
		3,1,2, 1,6,2, 6,3,2,
		1,4,5, 5,6,1, 6,5,4
};
private ShortBuffer pbuff;
private FloatBuffer vertBuff;
public Cube(){
	ByteBuffer bytebuff = ByteBuffer.allocateDirect(vertices.length * 4);
	bytebuff.order(ByteOrder.nativeOrder());
	vertBuff = bytebuff.asFloatBuffer();
	vertBuff.put(vertices);
	vertBuff.position(0);
	ByteBuffer pbbuff = ByteBuffer.allocateDirect(pIndex.length * 2);
	pbbuff.order(ByteOrder.nativeOrder());
	pbuff = pbbuff.asShortBuffer();
	pbuff.put(pIndex);
	pbuff.position(0);
}
public void draw(GL10 gl){
	gl.glFrontFace(GL10.GL_CW);
	gl.glEnable(GL10.GL_CULL_FACE);
	gl.glCullFace(GL10.GL_BACK);
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertBuff);
	gl.glDrawElements(GL10.GL_TRIANGLES, pIndex.length, GL10.GL_UNSIGNED_SHORT, pbuff);
	gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	gl.glDisable(GL10.GL_CULL_FACE);
}
}

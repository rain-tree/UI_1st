package com.shape.home.equlizer;import android.content.Context;import android.graphics.LinearGradient;import android.graphics.Path;import Views.api.ShapeView;import Views.api.shapeImg;import android.graphics.Paint;import android.graphics.Canvas;import com.linedeer.player.Ui;public class bassDot extends shapeImg{ class Path0 extends Path{  	public Path0() {			moveTo(118.45f, 39.05f);			quadTo(122.4f, 49.3f, 122.4f, 61.2f);			quadTo(122.4f, 86.6f, 104.45f, 104.5f);			quadTo(93f, 115.9f, 78.55f, 120.05f);			quadTo(70.35f, 122.4f, 61.2f, 122.4f);			quadTo(52.05f, 122.4f, 43.85f, 120.05f);			quadTo(29.4f, 115.9f, 17.9f, 104.5f);			quadTo(0f, 86.6f, 0f, 61.2f);			quadTo(0f, 49.3f, 4f, 39.05f);			quadTo(8.35f, 27.45f, 17.9f, 17.95f);			quadTo(35.85f, 0f, 61.2f, 0f);			quadTo(86.55f, 0f, 104.45f, 17.95f);			quadTo(113.95f, 27.45f, 118.45f, 39.05f);	}}class Path1 extends Path{  	public Path1() {			moveTo(65.4f, 11.4f);			quadTo(63.65f, 13.2f, 61.2f, 13.2f);			quadTo(58.7f, 13.2f, 56.9f, 11.4f);			quadTo(55.2f, 9.7f, 55.2f, 7.2f);			quadTo(55.2f, 4.75f, 56.9f, 2.95f);			quadTo(58.7f, 1.2f, 61.2f, 1.2f);			quadTo(63.65f, 1.2f, 65.4f, 2.95f);			quadTo(67.2f, 4.75f, 67.2f, 7.2f);			quadTo(67.2f, 9.7f, 65.4f, 11.4f);	}}		public Paint P0 = new Paint(); 		public static int Color0 = (0x00d35d69); 		public Path0 S0 = new Path0();		public Paint P1 = new Paint(); 		public static int Color1 = (0xFFf35d69); 		public Path1 S1 = new Path1();		public static float Ht = 122.4F; 		public static float Wh = 122.4F;  		public bassDot(int width,int height,int x,int y) {			this.width = width;			this.height = height;			init((float)width/(float)Wh, (float)height/(float)Ht, x, y); 			LinearGradient Lg  = null;			S0.transform(matrix);			P0.setColor(0x00d35d69);			P0.setAntiAlias(true);			S1.transform(matrix);			P1.setColor(0xFFf35d69);			P1.setAntiAlias(true);			mask = S0;			maskPaint = P0;		} 		public void draw(Canvas canvas) { 			if(drawing){				canvas.drawPath(S0, P0);				canvas.drawPath(S1, P1);			}		} 		@Override 		public void setX(int x) {			super.setX(x);			 S0.transform(matrix);			 S1.transform(matrix);		} 		@Override 		public void setY(int x) {			super.setY(x);			 S0.transform(matrix);			 S1.transform(matrix);		} 		@Override 		public void setSize(int wh, int ht) {			super.setSize(wh, ht);			 S0.transform(matrix);			 S1.transform(matrix);		} 		 public static ShapeView getFMview(Context context,boolean mask){		   ShapeView view = new ShapeView(context, Ui.cd.getHt((int) Wh), Ui.cd.getHt((int) Ht));		   final bassDot viewImg = new bassDot(Ui.cd.getHt((int) Wh), Ui.cd.getHt((int) Ht),0,0);		   view.mask = mask;		   view.img = viewImg;		   viewImg.mask = viewImg.S0;		   viewImg.maskPaint = viewImg.P0; 		   return view;		 }      		 public static bassDot getShape(){		   final bassDot viewImg = new bassDot(Ui.cd.getHt((int) Wh), Ui.cd.getHt((int) Ht),0,0);		   viewImg.mask = viewImg.S0;		   viewImg.maskPaint = viewImg.P0; 		   return viewImg;		 }     }//Finished...! 
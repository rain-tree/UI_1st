package com.shape.home.equalizer;import android.content.Context;import android.graphics.LinearGradient;import android.graphics.Path;import Views.api.ShapeView;import Views.api.shapeImg;import android.graphics.Paint;import android.graphics.Canvas;import com.linedeer.player.Ui;public class volumeIcon extends shapeImg{ class Path0 extends Path{  	public Path0() {			moveTo(23.6f, 14.9f);			quadTo(23.6f, 18.65f, 21f, 21.35f);			lineTo(19.7f, 20.05f);			quadTo(21.8f, 17.95f, 21.8f, 14.95f);			quadTo(21.8f, 11.9f, 19.7f, 9.8f);			lineTo(21f, 8.55f);			quadTo(23.6f, 11.2f, 23.6f, 14.9f);			moveTo(18.85f, 10.65f);			quadTo(20.65f, 12.45f, 20.7f, 15f);			quadTo(20.7f, 17.5f, 18.85f, 19.3f);			lineTo(17.65f, 17.95f);			quadTo(18.9f, 16.7f, 18.9f, 14.95f);			quadTo(18.9f, 13.2f, 17.65f, 11.9f);			lineTo(18.85f, 10.65f);			moveTo(16.6f, 6.45f);			lineTo(16.6f, 23.6f);			lineTo(10.45f, 18.6f);			lineTo(6.4f, 18.6f);			lineTo(6.4f, 11.45f);			lineTo(10.45f, 11.45f);			lineTo(16.6f, 6.45f);	}}class Path1 extends Path{  	public Path1() {			moveTo(30f, 30f);			lineTo(0f, 30f);			lineTo(0f, 0f);			lineTo(30f, 0f);			lineTo(30f, 30f);	}}		public Paint P0 = new Paint(); 		public static int Color0 = (0xFFebb6bc); 		public Path0 S0 = new Path0();		public Paint P1 = new Paint(); 		public static int Color1 = (0x00d35d69); 		public Path1 S1 = new Path1();		public static float Ht = 30F; 		public static float Wh = 30F;  		public volumeIcon(int width,int height,int x,int y) {			this.width = width;			this.height = height;			init((float)width/(float)Wh, (float)height/(float)Ht, x, y); 			LinearGradient Lg  = null;			S0.transform(matrix);			P0.setColor(0xFFebb6bc);			P0.setAntiAlias(true);			S1.transform(matrix);			P1.setColor(0x00d35d69);			P1.setAntiAlias(true);			mask = S0;			maskPaint = P0;		} 		public void draw(Canvas canvas) { 			if(drawing){				canvas.drawPath(S0, P0);				canvas.drawPath(S1, P1);			}		} 		@Override 		public void setX(int x) {			super.setX(x);			 S0.transform(matrix);			 S1.transform(matrix);		} 		@Override 		public void setY(int x) {			super.setY(x);			 S0.transform(matrix);			 S1.transform(matrix);		} 		@Override 		public void setSize(int wh, int ht) {			super.setSize(wh, ht);			 S0.transform(matrix);			 S1.transform(matrix);		} 		 public static ShapeView getFMview(Context context,boolean mask){		   ShapeView view = new ShapeView(context, Ui.cd.getHt((int) Wh), Ui.cd.getHt((int) Ht));		   final volumeIcon viewImg = new volumeIcon(Ui.cd.getHt((int) Wh), Ui.cd.getHt((int) Ht),0,0);		   view.mask = mask;		   view.img = viewImg;		   viewImg.mask = viewImg.S0;		   viewImg.maskPaint = viewImg.P0; 		   return view;		 }      		 public static volumeIcon getShape(){		   final volumeIcon viewImg = new volumeIcon(Ui.cd.getHt((int) Wh), Ui.cd.getHt((int) Ht),0,0);		   viewImg.mask = viewImg.S0;		   viewImg.maskPaint = viewImg.P0; 		   return viewImg;		 }     }//Finished...! 
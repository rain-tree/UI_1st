package com.shape.Library.allsong;import android.content.Context;import android.graphics.LinearGradient;import android.graphics.Path;import Views.api.ShapeView;import Views.api.shapeImg;import android.graphics.Paint;import android.graphics.Canvas;import com.linedeer.player.Ui;public class radiusBorder extends shapeImg{ class Path0 extends Path{  	public Path0() {			moveTo(13f, 2f);			quadTo(2f, 2f, 2f, 13f);			lineTo(2f, 87f);			quadTo(2f, 98f, 13f, 98f);			lineTo(37f, 98f);			quadTo(48f, 98f, 48f, 87f);			lineTo(48f, 13f);			quadTo(48f, 2f, 37f, 2f);			lineTo(13f, 2f);			moveTo(13f, 0f);			lineTo(37f, 0f);			quadTo(50f, 0f, 50f, 13f);			lineTo(50f, 87f);			quadTo(50f, 100f, 37f, 100f);			lineTo(13f, 100f);			quadTo(0f, 100f, 0f, 87f);			lineTo(0f, 13f);			quadTo(0f, 0f, 13f, 0f);	}}		public Paint P0 = new Paint(); 		public static int Color0 = (0xFFd35d69); 		public Path0 S0 = new Path0();		public static float Ht = 100F; 		public static float Wh = 50F;  		public radiusBorder(int width,int height,int x,int y) {			this.width = width;			this.height = height;			init((float)width/(float)Wh, (float)height/(float)Ht, x, y); 			LinearGradient Lg  = null;			S0.transform(matrix);			P0.setColor(0xFFd35d69);			P0.setAntiAlias(true);			mask = S0;			maskPaint = P0;		} 		public void draw(Canvas canvas) { 			if(drawing){				canvas.drawPath(S0, P0);			}		} 		@Override 		public void setX(int x) {			super.setX(x);			 S0.transform(matrix);		} 		@Override 		public void setY(int x) {			super.setY(x);			 S0.transform(matrix);		} 		@Override 		public void setSize(int wh, int ht) {			super.setSize(wh, ht);			 S0.transform(matrix);		} 		 public static ShapeView getFMview(Context context,boolean mask){		   ShapeView view = new ShapeView(context, Ui.cd.getHt((int) Wh), Ui.cd.getHt((int) Ht));		   final radiusBorder viewImg = new radiusBorder(Ui.cd.getHt((int) Wh), Ui.cd.getHt((int) Ht),0,0);		   view.mask = mask;		   view.img = viewImg;		   viewImg.mask = viewImg.S0;		   viewImg.maskPaint = viewImg.P0; 		   return view;		 }      		 public static radiusBorder getShape(){		   final radiusBorder viewImg = new radiusBorder(Ui.cd.getHt((int) Wh), Ui.cd.getHt((int) Ht),0,0);		   viewImg.mask = viewImg.S0;		   viewImg.maskPaint = viewImg.P0; 		   return viewImg;		 }     }//Finished...! 
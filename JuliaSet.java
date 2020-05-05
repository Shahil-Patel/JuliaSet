import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
public class JuliaSet extends JPanel implements AdjustmentListener,ActionListener
{
	JFrame frame;
	JPanel scrollPanel;
	JPanel labelPanel;
	JPanel bigSouthPanel;
	JPanel rightPanel;
	JScrollBar aBar;
	JScrollBar bBar;
	JScrollBar zoomBar;
	JScrollBar satBar;
	JScrollBar brightBar;
	JLabel a;
	JLabel b;
	JLabel z;
	JLabel sat;
	JLabel bright;
	JLabel buttonLabel;
	JButton random;
	int w=1200;
	int h=800;
	double zoom=1.0;
	float saturation=1.0f;
	float brightness=1.0f;
	float maxIters=50.0f;
	double zx=0.0;
	double zy=0.0;
	double aVal=0.0;
	double bVal=0.0;
	BufferedImage image = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
	public JuliaSet()
	{
		frame=new JFrame("Julia Set Program");
		frame.add(this);

		aBar=new JScrollBar(JScrollBar.HORIZONTAL,0,0,-20000,20000);
		aBar.addAdjustmentListener(this);
		bBar=new JScrollBar(JScrollBar.HORIZONTAL,0,0,-20000,20000);
		bBar.addAdjustmentListener(this);
		zoomBar=new JScrollBar(JScrollBar.HORIZONTAL,10000,0,10000,300000);
		zoomBar.addAdjustmentListener(this);
		satBar=new JScrollBar(JScrollBar.HORIZONTAL,10000,0,1,10000);
		satBar.addAdjustmentListener(this);
		brightBar=new JScrollBar(JScrollBar.HORIZONTAL,10000,0,1,10000);
		brightBar.addAdjustmentListener(this);

		random=new JButton("Click Here");
		random.addActionListener(this);
		buttonLabel=new JLabel("Reset");
		rightPanel=new JPanel();
		rightPanel.setLayout(new GridLayout(2,1));
		rightPanel.add(buttonLabel);
		rightPanel.add(random);

		scrollPanel=new JPanel();
		scrollPanel.setLayout(new GridLayout(5,1));
		scrollPanel.add(aBar);
		scrollPanel.add(bBar);
		scrollPanel.add(zoomBar);
		scrollPanel.add(satBar);
		scrollPanel.add(brightBar);

		labelPanel=new JPanel();
		labelPanel.setLayout(new GridLayout(5,1));
		a=new JLabel("A Value: "+aVal);
		labelPanel.add(a);
		b=new JLabel("B Value: "+bVal);
		labelPanel.add(b);
		z=new JLabel("Zoom: "+zoom);
		labelPanel.add(z);
		sat=new JLabel("Saturation: "+saturation);
		labelPanel.add(sat);
		bright=new JLabel("Brightness: "+brightness);
		labelPanel.add(bright);

		bigSouthPanel=new JPanel();
		bigSouthPanel.setLayout(new BorderLayout());
		bigSouthPanel.add(scrollPanel,BorderLayout.CENTER);
		bigSouthPanel.add(labelPanel,BorderLayout.WEST);
		bigSouthPanel.add(rightPanel,BorderLayout.EAST);

		frame.add(bigSouthPanel,BorderLayout.SOUTH);
		frame.setSize(w,h);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for(int x=0;x<w;x++)
		{
			for(int y=0;y<h;y++)
			{
				float i=maxIters;
				zx=1.5*(x-.5*w)/(.5*zoom*w);
				zy=(y-.5*h)/(.5*zoom*h);
				while(i>0&&(zx*zx+zy*zy<6))
				{
					double diff=zx*zx-zy*zy+aVal;
					zy=2*zx*zy+bVal;
					zx=diff;
					i--;
				}
				int c;
				if(i>0)
					c=Color.HSBtoRGB((maxIters/i)%1,saturation,brightness);
				else c=Color.HSBtoRGB(maxIters/i,saturation,0);
				image.setRGB(x,y,c);
			}
		}
		g.drawImage(image,0,0,null);
	}
	public void adjustmentValueChanged(AdjustmentEvent e)
	{
		if(e.getSource()==aBar)
		{
			aVal=aBar.getValue()/10000.0;
			a.setText("A Value: "+aVal);
		}
		if(e.getSource()==bBar)
		{
			bVal=bBar.getValue()/10000.0;
			b.setText("B Value: "+bVal);
		}
		if(e.getSource()==zoomBar)
		{
			zoom=zoomBar.getValue()/10000.0;
			z.setText("Zoom: "+zoom);
		}
		if(e.getSource()==satBar)
		{
			saturation=satBar.getValue()/10000.0f;
			sat.setText("Saturation: "+saturation);
		}
		if(e.getSource()==brightBar)
		{
			brightness=brightBar.getValue()/10000.0f;
			bright.setText("Brightness: "+brightness);
		}

		repaint();
	}
	public void actionPerformed(ActionEvent e)
	{
		aBar.setValue(0);
		bBar.setValue(0);
		zoomBar.setValue(1);
		satBar.setValue(10000);
		brightBar.setValue(10000);
		a.setText("A Value: "+aVal);
		b.setText("B Value: "+bVal);
		z.setText("Zoom: "+zoom);
		sat.setText("Saturation: "+saturation);
		bright.setText("Brightness: "+brightness);
		repaint();
	}
	public static void main(String[] args)
	{
		JuliaSet app=new JuliaSet();
	}
}
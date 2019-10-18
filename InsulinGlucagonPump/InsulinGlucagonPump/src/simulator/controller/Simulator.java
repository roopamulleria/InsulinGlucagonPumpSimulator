package simulator.controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;

import org.jfree.chart.JFreeChart;

import simulator.view.Bolus;
import simulator.view.Home;
import simulator.view.MainFrame;

/**
*
* @author Roopa Chakrakodi
*/
public class Simulator {

	public static int insulinQty = 100;
	public static int glucagonnQty = 100;
	public static int sugarLevel = 100;
	static MainFrame frame;	
	public static int basalRate;
	public static Map basalMap = new HashMap();
	private static boolean autorun = true;
	
	public static int getInsulinQty() {
		return insulinQty;
	}

	public static void setInsulinQty(int qty) {
		insulinQty = qty;
	}
	
	public static int getGlucagonnQty() {
		return glucagonnQty;
	}

	public static void setGlucagonnQty(int glucagonnQty) {
		Simulator.glucagonnQty = glucagonnQty;
	}

	public static void main(String[] args) {
		
		frame = new MainFrame();
		frame.setVisible(true);
		Basal basal = new Basal();
		basal.simulate();
		
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	public static void inject() {

		System.out.println("==============================");
		
		
		if( Dose.compInsulinDose > 0 ) {
			System.out.println("before injecting, sugar level:"+Simulator.sugarLevel);
			int insulin = (int) Dose.compInsulinDose;
			System.out.println("insulin:"+Dose.compInsulinDose);
			System.out.println("glucagon:"+Dose.compGlucagonDose);
			Bolus.setComputedDose();
			injectInsulin(insulin);			
			reduceSugarLevel(insulin);

			
		} else if( Dose.compGlucagonDose > 0 ) {
			System.out.println("before injecting, sugar level:"+Simulator.sugarLevel);
			int glucagon = (int) Dose.compGlucagonDose;
			System.out.println("insulin:"+Dose.compInsulinDose);
			System.out.println("glucagon:"+Dose.compGlucagonDose);
			Bolus.setComputedDose();
			injectGlucagon(glucagon);
			increaseSugarLevel(glucagon);

		}
		System.out.println("after injecting sugar level:"+Simulator.sugarLevel);
		
		frame.resetWarning();
			
	}
	
	public static void reduceSugarLevel(int insulin) {
		
		int delay = 1000;
		int reduce = insulin * 4;
        //System.out.println("Injecting insulin dose: "+insulin);
        
        for(;reduce>0;reduce--){
        Timer timer3;
            timer3 = new Timer(delay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	                   
                    int sugarLevel = Simulator.sugarLevel;
                    sugarLevel--;
                    Simulator.sugarLevel = sugarLevel;
                    Bolus.setSugarLevel(sugarLevel);
                    Home.setSugarLevel(sugarLevel);
                    
                } 
            });
            timer3.setRepeats(false);
            timer3.start();
        }
		
	}

    public static void increaseSugarLevel(int glucagon) {
    	
    	int delay = 1000;
		int reduce = glucagon * 4;
        //System.out.println("Injecting glucagon dose: "+glucagon);
        
        for(;reduce>0;reduce--){
        Timer timer4;
            timer4 = new Timer(delay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	
                    int sugarLevel = Simulator.sugarLevel;
                    sugarLevel++;
                    Simulator.sugarLevel = sugarLevel;
                    Home.setSugarLevel(sugarLevel);
                    Bolus.setSugarLevel(sugarLevel);

                } 
            });
            timer4.setRepeats(false);
            timer4.start();
        }
		
	}
    
    /***
	 * Inject insulin
	 * @param dose
	 */
    public static void injectInsulin(int dose){
    	
    	int qty = getInsulinQty();
    	if( qty <= 0){
    		frame.showWarning(Messages.WARNING_04);
    	}
    	if( dose > qty ){
    		
    		qty = 0;
    		
    	} else {
    		
    		qty = qty - dose;
        	
    	}
    	//System.out.println("");
    	setInsulinQty(qty);
    	frame.setInsulin(qty);
    	 
    }
    
    
    /***
	 * Inject insulin
	 * @param dose
	 */
    public static void injectGlucagon(int dose){
    	
    	int qty = getGlucagonnQty();
    	if( qty <= 0){
    		frame.showWarning(Messages.WARNING_06);
    	}
    	if( dose > qty ){
    		
    		qty = 0;
    		
    	} else {
    		
    		qty = qty - dose;
        	
    	}

    	setGlucagonnQty(qty);
    	frame.setGlucagon(qty);
    	 
    }

	public static boolean isAutorun() {
		return autorun;
	}

	public static void setAutorun(boolean autorun) {
		Simulator.autorun = autorun;
	}
	
}

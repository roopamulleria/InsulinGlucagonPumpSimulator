package simulator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import simulator.view.Bolus;
import simulator.view.Home;

public class Basal {

	Timer timer1;
	int count = 0;
	static Home home;
	
	void simulate(){
		
		simulateInsulin();
		/*try{
		    Thread.sleep(100);		    
		} catch(Exception e) {
		   e.printStackTrace();
		}*/
		takeFood();
		
	}
	
	void simulateInsulin(){
		
		Timer insulinTimer;
		
		ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {	
            	
            	if( Simulator.isAutorun() == true) { // autorun mode
            		
            		int sugar_level = Simulator.sugarLevel;
            		
            		Dose.computeDose(sugar_level);
            		Dose.autorunSafetyCheck();
                	Simulator.inject();
                	
            	}               
               
            }
        };
        insulinTimer = new Timer(10000, listener);
        insulinTimer.start();
	}
	
	void takeFood(){
		//final int[] meal = { 25,25,35,10,15,20,-50,-15,-5 };
		final int[] meal = { 30,35,40,-70,-40 };
		final int len = meal.length -1;
		Timer foodTimer;
		
		ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {	
            	
            	if( Simulator.isAutorun() == true) { // autorun mode
            		
            		int sugar_level = Simulator.sugarLevel;
            		System.out.println("before meal sugar_level:"+sugar_level);
            		System.out.println("Taking meal :"+meal[count]);            		
            		sugar_level = sugar_level + meal[count];
            		System.out.println("after meal sugar_level:"+sugar_level);
                	Simulator.sugarLevel = sugar_level;
                	home.setSugarLevel(sugar_level);
                	Bolus.setSugarLevel(sugar_level); 
                	if( count == len ){
                		count = 0;
                	} else {
                		count++;
                	}
                	
            	}               
               
            }
        };
        //foodTimer = new Timer(30000, listener);
        foodTimer = new Timer(15000, listener);
        foodTimer.start();
		
	}
	public void inject() {

		if( Dose.compInsulinDose > 0 ) {
			
			int insulin = (int) Dose.compInsulinDose;
			Simulator.injectInsulin(insulin);
			Simulator.reduceSugarLevel(insulin);
			
		} else if( Dose.compGlucagonDose > 0 ) {
			
			int glucagon = (int) Dose.compGlucagonDose;
			Simulator.injectGlucagon(glucagon);
			Simulator.increaseSugarLevel(glucagon);
		}				
			
	}
}

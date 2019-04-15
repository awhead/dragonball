package com.dae.dragonball.keyAllocation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyAllocation implements KeyListener {
    keyAllocation(Object obj){
	/*
	 * obj의 캐릭터값 받아오기
	 */
    }
    @Override
    public void keyPressed(KeyEvent e) {
	if (e.getKeyCode() == 'a' || e.getKeyCode() == 'A') {
	    /*
	     * obj캐릭터 값에 따라 스킬 출력할 수 있도록.
	     */
	} else if (e.getKeyCode() == 's' || e.getKeyCode() == 'S') {

	} else if (e.getKeyCode() == 'd' || e.getKeyCode() == 'D') {

	}
    }

    @Override
    public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub

    }

}

package com.MeMez.CloneBots;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {
	public void nativeKeyPressed(NativeKeyEvent e) {
		//System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

		if(e.getKeyCode() == NativeKeyEvent.VC_C) {
			if(App.doChatSpam == false) {
				App.doChatSpam = true;
			}
		}
		if(e.getKeyCode() == NativeKeyEvent.VC_R) {
			if(App.doEject == false) {
				App.doEject = true;
			}
		}
		if(e.getKeyCode() == NativeKeyEvent.VC_E) {
			if(App.doSplit == false) {
				App.doSplit = true;
			}
		}
		/*if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
			if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
				try {
					GlobalScreen.unregisterNativeHook();
				} catch (NativeHookException ex) {
					ex.printStackTrace();
				}
			}
		}*/
	}

	public void nativeKeyReleased(NativeKeyEvent e) {
		if(e.getKeyCode() == NativeKeyEvent.VC_C) {
			App.doChatSpam = false;
		}
		if(e.getKeyCode() == NativeKeyEvent.VC_E) {
			App.doSplit = false;
		}
		if(e.getKeyCode() == NativeKeyEvent.VC_R) {
			App.doEject = false;
		}
		//System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
	}

	public void nativeKeyTyped(NativeKeyEvent e) {
		//System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
	}
}
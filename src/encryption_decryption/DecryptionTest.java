package encryption_decryption;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DecryptionTest implements ChangeListener, ActionListener {
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	JLabel label = new JLabel();
	int key = -200;
	HashMap<Integer, String> map = new HashMap<Integer, String>();
	int[] top10 = new int[10];
	
	String encrypted = "– ë’¤¶ŒæÄž×Q·¥æá—³åÜÈ¥ÛŸ;XràÞP´ÛÝÈ¥àQ–Æ¡àá£°âÞÖSÓ£–n¤Ýß ³å™ÄSÖ–£·§Ýæ™½Ñ™Ò™’’Q¼šèä™ªŒÚÆœÖQ“¯¤Ù PžÔÞÑSàš¥Àš×’‘ªÕÝƒœåQž·©ÙÖP¾ÕíËSá¥™³£”ÕŸ´ÜèØ¡Ö¤]n¥ÜÛ£gÕìƒ–Ó³•”à™»ÞÚ×œáŸ_n~Ùä“¼ÞòSå¦˜¯£ ’“¶ÞäSÕ ¥Â âžP¨ÚÝƒ¢æ™–ÀQçç’ºàÚÑ–×¤Q±’â’’¬ŒîÖ˜ÖQ’ÁQèÚ•gÚâ×¥Ó¥š¼˜”Ó—¬Úí‘a _¨³àžP¬ÚèØšÚQ ´QèÚ•gàáÈ¢äª^n â’¤¶ŒíË˜’–©¾ãå™½Ñì‘=|z¢ƒÃ¹| ¯¾µ|ÀQty©’X•»¬Œf|;b|QºÛœ³ŒÚƒj§ž|QÖ×‘²Ñëƒ§áQ¥¶–”£cgÙå‘SÞ–§³”é™»Ô™É¨ßšŸµQâÛ¤¹ÕÜƒ”Õš•|;~¤^g¼åÄ–×Q“³’ß×¢gÕçƒ”àQš±–”Ô‘»Ô™Ä¡ÖQ³¥”Û¤gÏèÒŸ’¥ n’æá¥µÐ™“SÖ–˜À–Ùå^Qv¬‘S³••nd­’³š™Ò™’—¦»šâÙPºáåÉ¨äš”n’×Û”uŒÍË˜’¤¥À âÙ•¹ŒíË˜’“–Â¥Ùä^g®Þƒ¦ç£–n¥ã’ ¶áëƒ¦Þ ¨ºª”Óž«ŒÜÄ¥×—¦ºí :Q §ƒŠÓ¥”¶QèÚ•gàÞÐ£×£’Â¦æ×P¾ÕíËSÓQ”³ŸèÛ—¹ÍÝÈSæ™–Àžãß•»Ñë‘S»—Q·¥”Ù•»ß™×¢áQ™½¥ ’£»Ûéƒ£á¦£·ŸÛ’¤¯Ñ™Ö¨Þ—¦Àš×’‘ªÕÝƒ¨à¥šºQÝæPªÛèÏ¦ ;;ƒ_”É˜¬Ú™×›×Q’±šØ’£¶Øî×¢à¤Q¯£Ù’‘©Ûî×S£fQ²–Ûä•¬ß™¦_’’•²QÕ’–¬ã™Ç¥á¡¤n Ú’—³åÜÈ¥ÛŸQÅšèÚP¨Ú™È¬×•£½¡ä×¢sŒÌ¯‚É}ŠzQéà¤°Ø™×›×Q–¼¥Ýä•gßîÕ™Ó”–n Ú’¤¯Ñ™Ä–Û•Q·¤”ÕŸ½ÑëÈ—’¨šÂ™”Û¤uvƒ™a’z—n¥Ü×P»ÑæÓSäš¤³¤”Ó’¶âÞƒf¢Q•³˜æ×•º˜™Ü¢çQ¨·à’’³Ûðƒ¬á¦£Á–àØP¼Ü§ƒwáŸ¥nÙæP°à™Õœå–Q¯“ãè•gŸ©ƒ—×˜£³–ç :Q£§ƒz×Ÿ¥ºª”å¤°Þ™×›×QŸ·¥æÓ¤¬Ð™Ðœê¥¦À–¢’„¯Ñ™Ñœæ£ µíÕ•¹ÕçƒªÛn—ãägÛçƒ§á¡Q½—”æ˜¬ŒÚÆœÖQ’¼•”æ˜¬ŒìØŸØ¦£·””Ó“°Ð™ÚœÞQ¯“çá¢©ŒíË˜’–©±–çåP¾ÍíÈ¥ ;;†_”Æ¢¨ÚìÉ˜äQ¥¶–”åŸ³áíÌ¢àQ¥½QÕàŸ»ÔÞÕSÔ–’¹–æ’Ÿ­ŒðÄ§×£_n…Ü×PµÕíÕ¢’¨šº”å•»àåÈSáŸQÂ™Ù’’¶àíÒ ’’Ÿ²Qáá£»ŒèÉSæ™–n’×Û”gÏÚÑSÔ–Q²£ÕÛž¬Ð™ÄªÓª_X;­ P™ÑæÒ©×Q¥¶–”à™»ÞèƒªÛ¥™n’â’•ÀÑÝÕ¢â¡–ÀQœæ˜°ß™ÚœÞQ¼ è’’¬ŒÞÄ¦ëZQ¯ŸØ’”¹ÛéƒœæQš¼¥ã’‘gßèÇœçžQ°š×Ó¢©ÛçÄ§×QY°’ßÛž®ŒìÒ—ÓZQ»šìæ¥¹Ñ™×¢’Ÿ–Ã¥æÓœ°æÞƒ¥×ž’·ŸÝà—gÍÜÌ— ;;a¢’‚¬ÙèÙ˜’¥™³QâÛ¤¹Û™É¥ážQÂ™Ù’’°ÏÚÕ•áŸ’Â–”é™»Ô™×›×Q–Ç–ØäŸ·ÜÞÕa|;b_”ËŸ¼ŒçÒª’™’Ä–”à™»ÞèSä–’²ª”ØŸ¹ŒîÖ˜ QzÂQçÚŸ¼ØÝƒ•ç£Ÿn¨Ýæ˜gÍ™ÆŸ×’£n“àç•gÒåÄ ×Qš´QÝæP°ß™Ò™’•–±–âæP·áëÌ§ë_";
	public static void main(String[] args) {
		DecryptionTest dt = new DecryptionTest();
		dt.start();
	}
	
	void start() {
		System.out.println(encrypted.length());
//		for(int  i = 0; i < 26; i++) {
//			System.out.println(encrypted.charAt(i) + " - " + (int)encrypted.charAt(i));
//		}
		printStats();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setPreferredSize(new Dimension(820, 900));
		label.setPreferredSize(new Dimension(800, 800));
		panel.add(createSlider());
		panel.add(label);
		frame.add(panel);
		frame.pack();
		Timer timer = new Timer(300, this);
		timer.start();
		fillMap();
		getTop10();
	}
	
	void printStats() {
		HashMap<Character, Integer> symbols = new HashMap<Character, Integer>();
		for(int i = 0; i < encrypted.length(); i++) {
			if(symbols.containsKey(encrypted.charAt(i))) {
				symbols.put(encrypted.charAt(i), symbols.get(encrypted.charAt(i))+1);
			}
			else {
				symbols.put(encrypted.charAt(i), 1);
			}
		}
		System.out.println("Total unique symbol count: " + symbols.keySet().size());
		for(Character c : symbols.keySet()) {
			System.out.println(c + ":" + symbols.get(c));
		}
	}
	
	JSlider createSlider() {
		JSlider slider = new JSlider(JSlider.HORIZONTAL,
                0, 9, 1);
		slider.addChangeListener(this);
		
		//Turn on labels at major tick marks.
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setPreferredSize(new Dimension(800, 40));
		return slider;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		key = source.getValue();
		updateLabel();
	}
	
	public void updateLabel() {
		
		String decrypted = map.get(top10[key]);
		label.setText(decrypted);
		frame.pack();
	}
	
	void fillMap() {
		for(int i = 0; i <= 255; i++) {
			shift(encrypted, i);
		}
	}
	
	void getTop10() {
		for(Integer key : map.keySet()) {
			int lowest = getLowest();
			if(key > top10[lowest]) {
				top10[lowest] = key;
			}
		}
	}
	
	int getLowest() {
		int lowest = Integer.MAX_VALUE;
		int index = -1;
		for(int i = 0; i < 10; i++) {
			if(top10[i] < lowest) {
				lowest = top10[i];
				index = i;
			}
		}
		return index;
	}
	
	String shift(String enc, int dist) {
		String dec = "<html>";
		int count = 0;
		int total = 0;
		for(int i = 0; i < enc.length(); i++) {
			if(i % 80 == 0 && i != 0) {
				dec += "<br>";
			}
			int c = (enc.charAt(i) + dist) % 255;
			if(c > 31 && c < 122) count++;
			dec += (char)(c);
			total++;
		}
		dec+="</html>";
		map.put(count, dec);
		return dec;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

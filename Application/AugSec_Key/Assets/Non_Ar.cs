using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using System.Text;
using System;
using System.Diagnostics;
using System.Security.Cryptography;

public class Non_Ar : MonoBehaviour {
	public Button adder;
	public Button remover;
	public Button replacer;
	public Button sender;
	public Button back;
	public Button refresh;
	GameObject moveAble;
	List<GameObject> theBody = new List<GameObject>();
	string post = "";//string form of the position
	int pos = -1;// the position of the object 
	bool show_objects = false,
	end_objects = false,
	show_pos_chooser =false,
	end_pos_chooser= false,
	chosen = false,
	show_replace = false, 
	show_color = false,
	end_color = false;
	GameObject new_object = null;

	void Start () {
		moveAble = GameObject.Find ("Objects");
		adder.GetComponent<Button> ().onClick.AddListener (adding);
		remover.GetComponent<Button> ().onClick.AddListener (removing);
		replacer.GetComponent<Button> ().onClick.AddListener (replacing);
		back.GetComponent<Button>().onClick.AddListener(returning);
		sender.GetComponent<Button>().onClick.AddListener(sending);
		refresh.GetComponent<Button>().onClick.AddListener(refreshing);
	} 
	void Update(){
		if (end_objects) {
			show_objects = false;
			end_objects = false;
		} 
		if (end_pos_chooser) {
			show_pos_chooser = false;
			end_pos_chooser = false;
		}
		if (end_color) {
			show_color = false;
			end_color = false;
		}
	}
	bool check_for_digit(){ foreach(char tempo in post){if(tempo < '0' || tempo > '9') return false;} return true; }
	void OnGUI(){
		GUIStyle style = new GUIStyle (GUI.skin.textArea);
		style.fontSize = 46;
		if (show_pos_chooser) {
			post = GUI.TextField (new Rect (500, 650, 200, 200), post, style);
			if (GUI.Button (new Rect (500, 950, 200, 100), "EXIT", style)) {
				end_pos_chooser = true;
				adder.GetComponent<Button> ().onClick.AddListener (adding);// return them back 
				remover.GetComponent<Button> ().onClick.AddListener (removing);
				replacer.GetComponent<Button> ().onClick.AddListener (replacing);
			}
			if (GUI.Button (new Rect (500, 850, 200, 100), "done", style)) {
				end_pos_chooser = true;
				if (post != "" && check_for_digit ()) {//  if not changed then do not convert 
					int.TryParse (post, out pos);//convert the string to int 
					if (theBody.Count <= 0 || theBody.Count < pos) {
						adder.GetComponent<Button> ().onClick.AddListener (adding);// return them back 
						remover.GetComponent<Button> ().onClick.AddListener (removing);
						replacer.GetComponent<Button> ().onClick.AddListener (replacing);
					}//do nothing 
					else {//then add the object 
						show_replace = true;
						show_objects = true;
					}
				} else {
					adder.GetComponent<Button> ().onClick.AddListener (adding);// return them back 
					remover.GetComponent<Button> ().onClick.AddListener (removing);
					replacer.GetComponent<Button> ().onClick.AddListener (replacing);
				}
			}
		}
		if(show_objects){
			style.fontSize = 50;
			if (GUI.Button (new Rect (500, 350, 200, 100), "EXIT", style)) {
				end_objects = true;
				adder.GetComponent<Button>().onClick.AddListener(adding);// return them back 
				remover.GetComponent<Button>().onClick.AddListener(removing);
				replacer.GetComponent<Button>().onClick.AddListener (replacing);
				Destroy(new_object);
			}
			style.fontSize = 46;
			if (GUI.Button (new Rect (500, 450, 200, 100), "Cube", style)) {
				end_objects = true;  show_color = true;
				new_object = GameObject.CreatePrimitive(PrimitiveType.Cube);
				new_object.transform.localScale = new Vector3 (5F, 5F, 5F);
			}
			if (GUI.Button (new Rect (500, 550, 200, 100), "Cylinder", style)) {
				end_objects = true;show_color = true;
				new_object = GameObject.CreatePrimitive(PrimitiveType.Cylinder);
				new_object.transform.localScale = new Vector3 (5F, 2.7F, 5F);
			}
			if (GUI.Button (new Rect (500, 650, 200, 100), "Sphere", style)) {
				end_objects = true; show_color = true;
				new_object = GameObject.CreatePrimitive(PrimitiveType.Sphere);
				new_object.transform.localScale = new Vector3 (6F, 6F, 6F);
			}
			if (GUI.Button (new Rect (500, 750, 200, 100), "Ellipse", style)) {
				end_objects = true;  show_color = true;
				new_object = GameObject.CreatePrimitive(PrimitiveType.Sphere);
				new_object.transform.localScale = new Vector3 (7F, 5F, 6F);
			}
			style.fontSize = 38;
			if (GUI.Button (new Rect (500, 850, 200, 100), "Rectan Prism", style)) {
				end_objects = true;  show_color = true;
				new_object = GameObject.CreatePrimitive(PrimitiveType.Cube);
				new_object.transform.localScale = new Vector3 (7F, 5F, 6F);
			}
			style.fontSize = 46;
		}
		if(show_color){
			style.fontSize = 50;
			if (GUI.Button (new Rect (500, 350, 200, 100), "EXIT", style)) {
				chosen = false; show_replace = false; end_color = true;
				adder.GetComponent<Button>().onClick.AddListener(adding);// return them back 
				remover.GetComponent<Button>().onClick.AddListener(removing);
				replacer.GetComponent<Button>().onClick.AddListener (replacing);
				Destroy(new_object);
			}
			style.fontSize = 46;
			if (GUI.Button (new Rect (500, 450, 200, 100), "Black", style)) {
				chosen = true;end_color = true;
				new_object.GetComponent<Renderer> ().material.color = Color.black;
			}
			if (GUI.Button (new Rect (500, 550, 200, 100), "Blue", style)) {
				chosen = true;end_color = true;
				new_object.GetComponent<Renderer> ().material.color = Color.blue;
			}
			if (GUI.Button (new Rect (500, 650, 200, 100), "Cyan", style)) {
				chosen = true;end_color = true;
				new_object.GetComponent<Renderer> ().material.color = Color.cyan;
			}
			if (GUI.Button (new Rect (500, 750, 200, 100), "Grey", style)) {
				chosen = true;end_color = true;
				new_object.GetComponent<Renderer> ().material.color = Color.grey;
			}
			if (GUI.Button (new Rect (500, 850, 200, 100), "Green", style)) {
				chosen = true;end_color = true;
				new_object.GetComponent<Renderer> ().material.color = Color.green;
			}
			if (GUI.Button (new Rect (500, 950, 200, 100), "Magenta", style)) {
				chosen = true;end_color = true;
				new_object.GetComponent<Renderer> ().material.color = Color.magenta;
			}
			if (GUI.Button (new Rect (500, 1050, 200, 100), "Red", style)) {
				chosen = true;end_color = true;
				new_object.GetComponent<Renderer> ().material.color = Color.red;
			}
			if (GUI.Button (new Rect (500, 1150, 200, 100), "White", style)) {
				chosen = true;end_color = true;
				new_object.GetComponent<Renderer> ().material.color = Color.white;
			}
			if (GUI.Button (new Rect (500, 1250, 200, 100), "Yellow", style)) {
				chosen = true;end_color = true;
				new_object.GetComponent<Renderer> ().material.color = Color.yellow;
			}
			if (chosen && show_replace) {
				new_object.transform.position = new Vector3 (theBody [pos - 1].transform.position.x,
					theBody [pos - 1].transform.position.y ,
					theBody [pos - 1].transform.position.z);
				theBody [pos - 1].transform.parent = null;
				Destroy (theBody [pos - 1]);
				theBody.RemoveAt (pos - 1);
				theBody.Insert(pos-1 ,new_object);
				new_object.transform.parent = moveAble.transform;
				if (moveAble.transform.parent != null) {}
				chosen = false; 
				show_replace = false;
				new_object = null;
				adder.GetComponent<Button>().onClick.AddListener(adding);// return them back 
				remover.GetComponent<Button>().onClick.AddListener(removing);
				replacer.GetComponent<Button>().onClick.AddListener (replacing);
			}
			if (chosen) {
				if (theBody.Count == 0) {
					new_object.transform.position = new Vector3 (-100F, 0F, 0F);
					theBody.Add (new_object);
				} else {
					new_object.transform.position = new Vector3 (theBody [theBody.Count - 1].transform.position.x,
						theBody [theBody.Count - 1].transform.position.y + 6F,
						theBody [theBody.Count - 1].transform.position.z);
					theBody.Add (new_object);
				}
				new_object.transform.parent = moveAble.transform;
				if (moveAble.transform.parent != null) {}
				chosen = false;
				new_object = null;
				adder.GetComponent<Button>().onClick.AddListener(adding);// return them back 
				remover.GetComponent<Button>().onClick.AddListener(removing);
				replacer.GetComponent<Button>().onClick.AddListener (replacing);
			}
		}
	}
	void adding(){
		adder.GetComponent<Button>().onClick.RemoveAllListeners(); // remove the listeners so no interference will occur  
		remover.GetComponent<Button>().onClick.RemoveAllListeners();
		replacer.GetComponent<Button> ().onClick.RemoveAllListeners();
		show_objects = true;
	}
	void removing(){// simple , remove the top piece  
		if (theBody.Count == 0) {
		} else {
			theBody [theBody.Count - 1].transform.parent = null;
			Destroy(theBody [theBody.Count - 1]);
			theBody.RemoveAt(theBody.Count - 1);
		}
	}
	void replacing (){
		adder.GetComponent<Button>().onClick.RemoveAllListeners(); // remove the listeners so no interference will occur  
		remover.GetComponent<Button>().onClick.RemoveAllListeners();
		replacer.GetComponent<Button> ().onClick.RemoveAllListeners();
		show_pos_chooser = true;//show the keyboard
	}	
	void returning(){ SceneManager.LoadScene("Menu"); }
	void sending(){
		string key_data = "";

		for(int i =0; i < theBody.Count; i++){
			key_data += ("$$$$$$$$$$") + "\n";
			key_data += theBody [i].GetComponent<Renderer> ().material.color.ToString () + "\n";
			key_data += theBody [i].transform.localScale.ToString() + "\n";
		}
		key_data += "$$$$$$$$$$" + "\n";



		if (System.IO.File.Exists ("Um97.txt"))
			System.IO.File.Delete ("Um97.txt");
		System.IO.File.WriteAllBytes ("Um97.txt");
		Application.Quit ();
	}
	void refreshing(){ SceneManager.LoadScene("Non_Ar");}
}
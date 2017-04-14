using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEditor;
 
public class MainS : MonoBehaviour {
	public Button adder;
	public Button remover;
	public Button replacer;
	public Button dyButton;
	GameObject moveAble;
	List<GameObject> theBody = new List<GameObject>();
	string post = "";//string form of the position
	int pos = -1;// the position of the object 
	bool show_replace = false, 
	end_replace = false,
	show_objects = false,
	end_objects = false;
	TouchScreenKeyboard keyboard;



	void Start () {
		moveAble = GameObject.Find ("Objects");
		adder.GetComponent<Button>().onClick.AddListener(adding);
		remover.GetComponent<Button>().onClick.AddListener(removing);
		replacer.GetComponent<Button>().onClick.AddListener (replacing);
	}


	void Update(){
		if (end_replace) {
			show_replace = false;
			end_replace = false;
		}
		if (end_objects) {
			show_objects = false;
			end_objects = false;
		}
	}

	void OnGUI(){
		GUIStyle style = new GUIStyle (GUI.skin.textArea);
		style.fontSize = 46;
		if (show_replace) {
			post = GUI.TextField (new Rect (700, 750, 200, 200), post, style);
			if(GUI.Button(new Rect(700, 950, 200, 100), "done", style)){
				end_replace = true;
				if(post != ""){//  if not changed then do not convert 
					int.TryParse (post, out pos);//convert the string to int 
				} 
				if (theBody.Count <= 0 || theBody.Count < pos) {//do nothing 


				}else{//then add the object 
					GameObject new_object = GameObject.CreatePrimitive (PrimitiveType.Cube);
					new_object.GetComponent<Renderer> ().material.color = Color.black;
					new_object.transform.position = new Vector3 (theBody [pos - 1].transform.position.x,
						theBody [pos - 1].transform.position.y ,
						theBody [pos - 1].transform.position.z);
					new_object.transform.localScale = new Vector3 (5F, 5F, 5F);
					theBody [pos - 1].transform.parent = null;
					Destroy (theBody [pos - 1]);
					theBody.RemoveAt (pos - 1);
					theBody.Insert(pos-1 ,new_object);
					new_object.transform.parent = moveAble.transform;
					if (moveAble.transform.parent != null) {}
				}


				adder.GetComponent<Button>().onClick.AddListener(adding);// return them back 
				remover.GetComponent<Button>().onClick.AddListener(removing);
				replacer.GetComponent<Button>().onClick.AddListener (replacing);
			}
			keyboard = TouchScreenKeyboard.Open ("", TouchScreenKeyboardType.NumberPad);

		}
			
		if(show_objects){
			GameObject new_object = null;
			if (GUI.Button (new Rect (500, 450, 200, 100), "Cube", style)) {
				end_objects = true;
				new_object = GameObject.CreatePrimitive(PrimitiveType.Cube);
				new_object.GetComponent<Renderer> ().material.color = Color.white;
				if (theBody.Count == 0) {
					new_object.transform.position = new Vector3 (-100F, 0F, 0F);
					new_object.transform.localScale = new Vector3 (5F, 5F, 5F);
					theBody.Add(new_object);
				} else {
					new_object.transform.position = new Vector3 (theBody [theBody.Count - 1].transform.position.x,
						theBody [theBody.Count - 1].transform.position.y + 6F,
						theBody [theBody.Count - 1].transform.position.z);
					new_object.transform.localScale = new Vector3 (5F, 5F, 5F);
					theBody.Add(new_object);
				}
				new_object.transform.parent = moveAble.transform;
				if( moveAble.transform.parent != null ){}
			}


			if (GUI.Button (new Rect (500, 550, 200, 100), "Cylinder", style)) {
				end_objects = true;
				new_object = GameObject.CreatePrimitive(PrimitiveType.Cylinder);
				new_object.GetComponent<Renderer> ().material.color = Color.white;
				if (theBody.Count == 0) {
					new_object.transform.position = new Vector3 (-100F, 0F, 0F);
					new_object.transform.localScale = new Vector3 (5F, 2.7F, 5F);
					theBody.Add(new_object);
				} else {
					new_object.transform.position = new Vector3 (theBody [theBody.Count - 1].transform.position.x,
						theBody [theBody.Count - 1].transform.position.y + 6F,
						theBody [theBody.Count - 1].transform.position.z);
					new_object.transform.localScale = new Vector3 (5F, 2.7F, 5F);
					theBody.Add(new_object);
				}
				new_object.transform.parent = moveAble.transform;
				if( moveAble.transform.parent != null ){}
			}


			if (GUI.Button (new Rect (500, 650, 200, 100), "Sphere", style)) {
				end_objects = true;
				new_object = GameObject.CreatePrimitive(PrimitiveType.Sphere);
				new_object.GetComponent<Renderer> ().material.color = Color.white;
				if (theBody.Count == 0) {
					new_object.transform.position = new Vector3 (-100F, 0F, 0F);
					new_object.transform.localScale = new Vector3 (6F, 6F, 6F);
					theBody.Add(new_object);
				} else {
					new_object.transform.position = new Vector3 (theBody [theBody.Count - 1].transform.position.x,
						theBody [theBody.Count - 1].transform.position.y + 6F,
						theBody [theBody.Count - 1].transform.position.z);
					new_object.transform.localScale = new Vector3 (6F, 6F, 6F);
					theBody.Add(new_object);
				}
				new_object.transform.parent = moveAble.transform;
				if( moveAble.transform.parent != null ){}
			}


			if (GUI.Button (new Rect (500, 750, 200, 100), "Ellipse", style)) {
				end_objects = true;
				new_object = GameObject.CreatePrimitive(PrimitiveType.Sphere);
				new_object.GetComponent<Renderer> ().material.color = Color.white;
				if (theBody.Count == 0) {
					new_object.transform.position = new Vector3 (-100F, 0F, 0F);
					new_object.transform.localScale = new Vector3 (7F, 5F, 6F);
					theBody.Add(new_object);
				} else {
					new_object.transform.position = new Vector3 (theBody [theBody.Count - 1].transform.position.x,
						theBody [theBody.Count - 1].transform.position.y + 6F,
						theBody [theBody.Count - 1].transform.position.z);
					new_object.transform.localScale = new Vector3 (7F, 5F, 6F);
					theBody.Add(new_object);
				}
				new_object.transform.parent = moveAble.transform;
				if( moveAble.transform.parent != null ){}
			}

			if (GUI.Button (new Rect (500, 850, 200, 100), "Rectangular Prism", style)) {
				end_objects = true;
				new_object = GameObject.CreatePrimitive(PrimitiveType.Cube);
				new_object.GetComponent<Renderer> ().material.color = Color.white;
				if (theBody.Count == 0) {
					new_object.transform.position = new Vector3 (-100F, 0F, 0F);
					new_object.transform.localScale = new Vector3 (7F, 5F, 6F);
					theBody.Add(new_object);
				} else {
					new_object.transform.position = new Vector3 (theBody [theBody.Count - 1].transform.position.x,
						theBody [theBody.Count - 1].transform.position.y + 6F,
						theBody [theBody.Count - 1].transform.position.z);
					new_object.transform.localScale = new Vector3 (7F, 5F, 6F);
					theBody.Add(new_object);
				}
				new_object.transform.parent = moveAble.transform;
				if( moveAble.transform.parent != null ){}
			}
		}
			



	}



	void adding(){
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
		show_replace = true;//show the keyboard
	}		
}
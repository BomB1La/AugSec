﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEditor;

public class Menu : MonoBehaviour {
	public Button with_ar;
	public Button non_ar;
	public Button exit;

	void Start () {
		with_ar.GetComponent<Button> ().onClick.AddListener (start_ar);
		non_ar.GetComponent<Button> ().onClick.AddListener (start_non);
		exit.GetComponent<Button> ().onClick.AddListener (end_it);
	} 
	void start_ar(){ Application.LoadLevel("With_Ar"); }
	void start_non(){ Application.LoadLevel("Non_Ar"); }
	void end_it(){ Application.Quit (); }

}

using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class Menu : MonoBehaviour {
	public Button with_ar;
	public Button non_ar;
	public Button exit;

	void Start () {
		with_ar.GetComponent<Button> ().onClick.AddListener (start_ar);
		non_ar.GetComponent<Button> ().onClick.AddListener (start_non);
		exit.GetComponent<Button> ().onClick.AddListener (end_it);
		if (System.IO.File.Exists ("/storage/emulated/0/Android/data/com.augsec.AugSec_key/files/Um197"))
			System.IO.File.Delete ("/storage/emulated/0/Android/data/com.augsec.AugSec_key/files/Um197");
		if (System.IO.File.Exists ("/storage/emulated/0/Um97"))
			System.IO.File.Delete ("/storage/emulated/0/Um97");
	} 
	void start_ar(){ SceneManager.LoadScene("With_Ar"); }
	void start_non(){ SceneManager.LoadScene("Non_Ar"); }
	void end_it(){ Application.Quit (); }

}

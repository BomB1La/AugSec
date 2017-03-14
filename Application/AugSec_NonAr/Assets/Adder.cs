using UnityEngine;
using UnityEngine.UI;

public class Adder : MonoBehaviour {
	public Button adder;
 	public Rect window_Rect0 = new Rect(20, 20, 120, 50);
    public Rect window_Rect1 = new Rect(20, 100, 120, 50);
    void OnGUI() {
        GUI.color = Color.red;
        window_Rect0 = GUI.Window(0, window_Rect0, DoMyWindow, "Red Window");
        GUI.color = Color.green;
        window_Rect1 = GUI.Window(1, window_Rect1, DoMyWindow, "Green Window");
    }
    void DoMyWindow(int windowID) {
        if (GUI.Button(new Rect(10, 20, 100, 20), "Hello World"))
            print("Got a click in window with color " + GUI.color);
        
        GUI.DragWindow(new Rect(0, 0, 10000, 10000));
    }
	void Start () {
		Button btn = adder.GetComponent<Button>();
		btn.onClick.AddListener(TaskOnClick);
	}

	void TaskOnClick(){
		GameObject cube = GameObject.CreatePrimitive(PrimitiveType.Cube);
        cube.transform.position = new Vector3(4, 4, 4);
	}
}
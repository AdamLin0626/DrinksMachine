import tkinter as tk
import subprocess
import paho.mqtt.client as mqtt
import time

# --- 建立主視窗 ---
root = tk.Tk()
root.title("智慧飲品工坊控制面板")
root.geometry("800x600")           # 初始大小
root.resizable(False, False)       # 禁止調整視窗大小

# --- 指令對應 ---
command_map = {
    "motor": "motor.py",
    "camera": "camera.py",
}

# --- 執行指令 ---
def run_command(cmd):
    if cmd in command_map:
        subprocess.Popen(["python", command_map[cmd]])
    elif cmd == "shutdown_windows":
        print("謝謝光臨!!")
        time.sleep(1)
        root.destroy()
    else :
        print("無定義按鍵")



# --- MQTT 訊息處理 ---
def on_message(client, userdata, msg):
    command = msg.payload.decode()
    print(f"MQTT 收到指令: {command}")
    run_command(command)

# --- MQTT 初始化 ---
client = mqtt.Client()
client.on_message = on_message
client.connect("broker.MQTTGO.io")
client.subscribe("drinksmachine/test01")
client.loop_start()

# --- GUI 按鈕 ---
tk.Label(root, text="請選擇功能", font=("Arial", 32)).pack(pady=10)
tk.Button(root, text="啟動馬達", font=("Arial", 20), command=lambda: run_command("motor")).pack(anchor="nw")
tk.Button(root, text="拍照", font=("Arial", 20), command=lambda: run_command("camera")).pack(pady=5)
tk.Button(root, text="關機", font=("Arial", 20), command=lambda: run_command("shutdown_windows")).pack(pady=5)

# --- 啟動 GUI ---
root.mainloop()

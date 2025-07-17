import tkinter as tk
import subprocess
import paho.mqtt.client as mqtt
import time
import gettext
import os

# --- 選擇語言：zh_TW 或 en ---
lang = "zh_TW"

# --- 設定語系資料夾與 gettext ---
localedir = os.path.join(os.path.abspath(os.path.dirname(__file__)), 'locales')
language = gettext.translation("messages", localedir, languages=[lang], fallback=True)
language.install()
_ = language.gettext  # 簡化用法 _() = gettext()

# --- 建立主視窗 ---
root = tk.Tk()
root.title(_("Smart Drink Workshop Control Panel"))
root.geometry("800x600")
root.resizable(False, False)

# --- 指令對應 ---
command_map = {
    "motor": "Test/motor.py",
    "camera": "Test/camera.py",
}

# --- 執行指令 ---
def run_command(cmd):
    if cmd in command_map:
        subprocess.Popen(["python", command_map[cmd]])
    elif cmd == "shutdown_windows":
        print(_("Thank you! Shutting down..."))
        time.sleep(1)
        root.destroy()
    else:
        print(_("Unknown command"))

# --- MQTT 訊息處理 ---
def on_message(client, userdata, msg):
    command = msg.payload.decode()
    print(_("MQTT Received: ") + command)
    run_command(command)

# --- MQTT 初始化 ---
client = mqtt.Client()
client.on_message = on_message
client.connect("broker.MQTTGO.io")
client.subscribe("drinksmachine/test01")
client.loop_start()

# --- GUI 按鈕 ---
tk.Label(root, text=_("Please select a function"), font=("Arial", 32)).pack(pady=10)
tk.Button(root, text=_("Start Motor"), font=("Arial", 20), command=lambda: run_command("motor")).pack(anchor="nw")
tk.Button(root, text=_("Take Photo"), font=("Arial", 20), command=lambda: run_command("camera")).pack(pady=5)
tk.Button(root, text=_("Shutdown"), font=("Arial", 20), command=lambda: run_command("shutdown_windows")).pack(pady=5)

# --- 啟動 GUI ---
root.mainloop()

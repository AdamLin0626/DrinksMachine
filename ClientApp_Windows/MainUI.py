import customtkinter as ctk
import subprocess
import paho.mqtt.client as mqtt
import time
import gettext
import os

# --- 初始語言與主題 ---
lang = "zh_TW"
theme = "dark"

# --- 取得語系翻譯檔案路徑，並安裝 gettext ---
def setup_language(lang_code):
    global _
    localedir = os.path.join(os.path.abspath(os.path.dirname(__file__)), 'locales')
    language = gettext.translation("messages", localedir, languages=[lang_code], fallback=True)
    language.install()
    _ = language.gettext

setup_language(lang)  # 預設語系為 zh_TW

# --- 設定 customtkinter 的主題與樣式 ---
ctk.set_appearance_mode(theme)  # 可為 "light", "dark", 或 "system"
ctk.set_default_color_theme("blue")  # 主題色

# --- 建立主視窗 ---
root = ctk.CTk()
root.title(_("Smart Drink Workshop Control Panel"))
root.geometry("800x600")
root.resizable(False, False)

# --- 指令對應表 ---
command_map = {
    "motor": "Test/motor.py",
    "camera": "Test/camera.py",
}

# --- 執行外部指令或關閉程式 ---
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

# --- 切換語言（重新載入 gettext 並更新介面文字）---
def change_language(choice):
    setup_language(choice)
    # 重新更新所有文字
    title_label.configure(text=_("Smart Drink Workshop Control Panel"))
    function_label.configure(text=_("Please select a function"))
    motor_btn.configure(text=_("Start Motor"))
    camera_btn.configure(text=_("Take Photo"))
    shutdown_btn.configure(text=_("Shutdown"))
    lang_menu.set(choice)
    theme_label.configure(text=_("Theme"))
    lang_label.configure(text=_("Language"))

# --- 切換主題 ---
def change_theme(choice):
    ctk.set_appearance_mode(choice)

# --- 介面元件設計 ---
title_label = ctk.CTkLabel(root, text=_("Smart Drink Workshop Control Panel"), font=("Arial", 28))
title_label.pack(pady=20)

# --- 主控制區塊 ---
frame = ctk.CTkFrame(root)
frame.pack(pady=10, padx=20, fill="both", expand=True)

function_label = ctk.CTkLabel(frame, text=_("Please select a function"), font=("Arial", 24))
function_label.pack(pady=10)

motor_btn = ctk.CTkButton(frame, text=_("Start Motor"), font=("Arial", 20), command=lambda: run_command("motor"))
motor_btn.pack(pady=10)

camera_btn = ctk.CTkButton(frame, text=_("Take Photo"), font=("Arial", 20), command=lambda: run_command("camera"))
camera_btn.pack(pady=10)

shutdown_btn = ctk.CTkButton(frame, text=_("Shutdown"), font=("Arial", 20), fg_color="red", hover_color="darkred",
                              command=lambda: run_command("shutdown_windows"))
shutdown_btn.pack(pady=10)

# --- 語言選單（下拉選單）---
lang_label = ctk.CTkLabel(root, text=_("Language"), font=("Arial", 16))
lang_label.pack(pady=(20, 5))

lang_menu = ctk.CTkOptionMenu(root, values=["zh_TW", "en"], command=change_language)
lang_menu.set(lang)  # 預設語言
lang_menu.pack()

# --- 主題選單（下拉選單）---
theme_label = ctk.CTkLabel(root, text=_("Theme"), font=("Arial", 16))
theme_label.pack(pady=(20, 5))

theme_menu = ctk.CTkOptionMenu(root, values=["light", "dark"], command=change_theme)
theme_menu.set(theme)  # 預設主題
theme_menu.pack()

# --- 啟動主視窗 ---
root.mainloop()

import customtkinter as ctk

# ----------------- 初始化 customtkinter -----------------
ctk.set_appearance_mode("light")  # 可切換為 "dark"
ctk.set_default_color_theme("blue")

# ----------------- 建立主視窗 -----------------
app = ctk.CTk()
app.geometry("1280x800")
app.title("Smart Drink Workshop Control Panel")

# ----------------- 上方控制列 -----------------
top_frame = ctk.CTkFrame(app, fg_color="transparent")
top_frame.pack(fill="x", pady=20)

# ⏻ 電源按鈕（點擊後關閉主視窗）
power_btn = ctk.CTkButton(
    top_frame,
    text="⏻",
    width=60,
    height=40,
    font=("Arial", 24),
    command=app.destroy
)
power_btn.pack(side="left", padx=20)

# 中央標題文字
title_label = ctk.CTkLabel(
    top_frame,
    text="Smart Drink Workshop Control Panel",
    font=("Orbitron", 36)
)
title_label.pack(side="left", expand=True)

# 模式切換開關（亮暗模式）
def toggle_mode():
    current = ctk.get_appearance_mode()
    ctk.set_appearance_mode("light" if current == "Dark" else "dark")

mode_switch = ctk.CTkSwitch(top_frame, text="", command=toggle_mode)
mode_switch.pack(side="right", padx=20)

# ----------------- 主體區塊 -----------------
main_frame = ctk.CTkFrame(app)
main_frame.pack(fill="both", expand=True, padx=20, pady=10)

# ===== 左側：模式選擇區塊 =====
left_panel = ctk.CTkFrame(main_frame, width=240)
left_panel.pack(side="left", fill="y", padx=10, pady=10)

mode_label = ctk.CTkLabel(left_panel, text="CHOOSE THE MODE", font=("Orbitron", 21))
mode_label.pack(pady=30)

# 模式選擇按鈕（放大）
for i in range(3):
    btn = ctk.CTkButton(left_panel, text=f"模式 {i+1}", height=75, font=("Arial", 20))
    btn.pack(pady=20, padx=20, fill="x")

# ===== 中間：Your Choice 選單（含 Scroll） =====
center_frame = ctk.CTkFrame(main_frame)
center_frame.pack(side="left", fill="both", expand=True, padx=10, pady=10)

choice_label = ctk.CTkLabel(center_frame, text="Your Choice", font=("Orbitron", 24))
choice_label.pack(anchor="w", padx=20, pady=(20, 10))

# Scrollable 選單容器
choice_scroll = ctk.CTkScrollableFrame(center_frame, height=400)
choice_scroll.pack(padx=20, pady=10, fill="both", expand=True)

# 模擬 8 組選項（4 格為一排）
for i in range(0, 8, 2):
    row_frame = ctk.CTkFrame(choice_scroll, fg_color="transparent")
    row_frame.pack(pady=10, padx=10)
    for j in range(2):
        # 每格選單方塊
        box = ctk.CTkFrame(row_frame, width=200, height=150)
        box.pack(side="left", padx=20)

        # 左右控制按鈕
        btn_left = ctk.CTkButton(box, text="<", width=40, font=("Arial", 20))
        btn_left.place(relx=0.05, rely=0.8)
        btn_right = ctk.CTkButton(box, text=">", width=40, font=("Arial", 20))
        btn_right.place(relx=0.75, rely=0.8)

# ===== 右側：User Guide 區塊（含 Scroll） =====
right_panel = ctk.CTkFrame(main_frame, width=350)
right_panel.pack(side="right", fill="y", padx=10, pady=10)

guide_label = ctk.CTkLabel(right_panel, text="User Guide", font=("Orbitron", 24))
guide_label.pack(anchor="w", padx=20, pady=(20, 10))

guide_scroll = ctk.CTkScrollableFrame(right_panel, height=600)
guide_scroll.pack(fill="both", expand=True, padx=20, pady=10)

# 多行說明文字（示意內容）
long_guide_text = (
    "Office ipsum you must be muted.\n"
    "Requirements marketing wiggle invested will plane must.\n"
    "Now these reinvent or encourage cadence this member encourage.\n"
    "First discussions eat when join low-hanging.\n\n"
    "Adoption solutionize best asserts window productive were pole attached.\n"
    "Productive functional up email without three in any shark.\n"
    "Guys email boardroom innovation mindfulness.\n"
    "More lines here... (expandable)\n" * 3
)

guide_label_long = ctk.CTkLabel(
    guide_scroll,
    text=long_guide_text,
    justify="left",
    wraplength=300,
    font=("Arial", 18)
)
guide_label_long.pack(anchor="w")

# ----------------- 底部確認區域 -----------------
bottom_frame = ctk.CTkFrame(app, height=100)
bottom_frame.pack(fill="x", padx=30, pady=20)

confirm_label = ctk.CTkLabel(bottom_frame, text="確認訂單中...", font=("Arial", 24))
confirm_label.pack(pady=(10, 5))

progress_bar = ctk.CTkProgressBar(bottom_frame, height=18)
progress_bar.pack(fill="x", padx=120, pady=10)
progress_bar.set(0.6)  # 示意進度，可動態設定

# ----------------- 啟動主視窗 -----------------
app.mainloop()

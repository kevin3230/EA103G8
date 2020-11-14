DROP TABLE FAQ;
DROP TABLE FAQ_TYPE;
DROP SEQUENCE SEQ_FAQNO;
DROP SEQUENCE SEQ_FAQTNO;

CREATE SEQUENCE SEQ_FAQNO
MAXVALUE 9999999
NOCYCLE
NOCACHE;

CREATE SEQUENCE SEQ_FAQTNO
MAXVALUE 9999999
NOCYCLE
NOCACHE;

CREATE TABLE FAQ_TYPE (
    FAQT_NO CHAR(10) PRIMARY KEY NOT NULL,
    FAQT_NAME VARCHAR2(20 CHAR) NOT NULL
);

CREATE TABLE FAQ (
    FAQ_NO CHAR(10) PRIMARY KEY NOT NULL,
    FAQ_FAQTNO CHAR(10),
    FAQ_TITLE VARCHAR2(30 CHAR) NOT NULL,
    FAQ_CONTENT VARCHAR2(300 CHAR),
    FAQ_EDIT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FAQ_STAT NUMBER(1) DEFAULT 0 NOT NULL,
    FAQ_ADMINISNO CHAR(10),
    CONSTRAINT FK_FAQ_ADMINIS
    FOREIGN KEY(FAQ_ADMINISNO) REFERENCES ADMINIS(ADMINIS_NO)  
);


INSERT INTO FAQ_TYPE(FAQT_NO, FAQT_NAME)
VALUES('FAQT' || LPAD(SEQ_FAQTNO.NEXTVAL, 6, '0'), '營位相關');

INSERT INTO FAQ_TYPE(FAQT_NO, FAQT_NAME)
VALUES('FAQT' || LPAD(SEQ_FAQTNO.NEXTVAL, 6, '0'), '裝備租借');

INSERT INTO FAQ_TYPE(FAQT_NO, FAQT_NAME)
VALUES('FAQT' || LPAD(SEQ_FAQTNO.NEXTVAL, 6, '0'), '付款相關');

INSERT INTO FAQ_TYPE(FAQT_NO, FAQT_NAME)
VALUES('FAQT' || LPAD(SEQ_FAQTNO.NEXTVAL, 6, '0'), '網站使用');

INSERT INTO FAQ_TYPE(FAQT_NO, FAQT_NAME)
VALUES('FAQT' || LPAD(SEQ_FAQTNO.NEXTVAL, 6, '0'), '其他問題');


INSERT INTO FAQ
(FAQ_NO, FAQ_FAQTNO, FAQ_TITLE, FAQ_CONTENT, FAQ_STAT, FAQ_ADMINISNO)
VALUES('FAQ' || LPAD(SEQ_FAQNO.NEXTVAL, 7, '0'), 'FAQT000001', 
'營位是否有附設浴室？',
'大部分的營區業者都會附設浴室，實際建議向業者詢問。',
2, 'A000000005');

INSERT INTO FAQ
(FAQ_NO, FAQ_FAQTNO, FAQ_TITLE, FAQ_CONTENT, FAQ_STAT, FAQ_ADMINISNO)
VALUES('FAQ' || LPAD(SEQ_FAQNO.NEXTVAL, 7, '0'), 'FAQT000001',
'營位是否有停車場？',
'大部分的營區業者都有停車場，但不一定在營位附近，實際建議向業者詢問。',
2, 'A000000005');

INSERT INTO FAQ
(FAQ_NO, FAQ_FAQTNO, FAQ_TITLE, FAQ_CONTENT, FAQ_STAT, FAQ_ADMINISNO)
VALUES('FAQ' || LPAD(SEQ_FAQNO.NEXTVAL, 7, '0'), 'FAQT000001', 
'營位是否有插座？',
'實際建議向業者詢問，目前一般業者的營位基本上都會附設複數插座。',
2, 'A000000005');

INSERT INTO FAQ
(FAQ_NO, FAQ_FAQTNO, FAQ_TITLE, FAQ_CONTENT, FAQ_STAT, FAQ_ADMINISNO)
VALUES('FAQ' || LPAD(SEQ_FAQNO.NEXTVAL, 7, '0'), 'FAQT000002',
'網站上沒有想要的裝備？',
'可能是業者沒放上去，或者不用收費，不妨向業者詢問。',
0, 'A000000001');

INSERT INTO FAQ
(FAQ_NO, FAQ_FAQTNO, FAQ_TITLE, FAQ_CONTENT, FAQ_STAT, FAQ_ADMINISNO)
VALUES('FAQ' || LPAD(SEQ_FAQNO.NEXTVAL, 7, '0'), 'FAQT000002',
'裝備租借起算與結算時間點？',
'同check in 與 check out 時間。',
0, 'A000000001');

INSERT INTO FAQ
(FAQ_NO, FAQ_FAQTNO, FAQ_TITLE, FAQ_CONTENT, FAQ_STAT, FAQ_ADMINISNO)
VALUES('FAQ' || LPAD(SEQ_FAQNO.NEXTVAL, 7, '0'), 'FAQT000002', 
'裝備損毀怎麼辦？', 
'請與業者友好協商。',
0, 'A000000001');

INSERT INTO FAQ
(FAQ_NO, FAQ_FAQTNO, FAQ_TITLE, FAQ_CONTENT, FAQ_STAT, FAQ_ADMINISNO)
VALUES('FAQ' || LPAD(SEQ_FAQNO.NEXTVAL, 7, '0'), 'FAQT000003', 
'業者通知信用卡被重複刷12次？', 
'請撥打警政署165反詐騙專線。',
0, 'A000000001');

INSERT INTO FAQ
(FAQ_NO, FAQ_FAQTNO, FAQ_TITLE, FAQ_CONTENT, FAQ_STAT, FAQ_ADMINISNO)
VALUES('FAQ' || LPAD(SEQ_FAQNO.NEXTVAL, 7, '0'), 'FAQT000003', 
'可否使用匯款？', 
'目前本系統無此功能。',
0, 'A000000001');

INSERT INTO FAQ
(FAQ_NO, FAQ_FAQTNO, FAQ_TITLE, FAQ_CONTENT, FAQ_STAT, FAQ_ADMINISNO)
VALUES('FAQ' || LPAD(SEQ_FAQNO.NEXTVAL, 7, '0'), 'FAQT000003', 
'取消訂位的違約金？', 
'依各業者規定所示。',
0, 'A000000001');

INSERT INTO FAQ
(FAQ_NO, FAQ_FAQTNO, FAQ_TITLE, FAQ_CONTENT, FAQ_STAT, FAQ_ADMINISNO)
VALUES('FAQ' || LPAD(SEQ_FAQNO.NEXTVAL, 7, '0'), 'FAQT000004', 
'網站使用體驗不佳？', 
'怪我囉？',
0, 'A000000001');

INSERT INTO FAQ
(FAQ_NO, FAQ_FAQTNO, FAQ_TITLE, FAQ_CONTENT, FAQ_STAT, FAQ_ADMINISNO)
VALUES('FAQ' || LPAD(SEQ_FAQNO.NEXTVAL, 7, '0'), 'FAQT000004', 
'網站美術設計待加強？', 
'JAVA班莫強求',
0, 'A000000001');

INSERT INTO FAQ
(FAQ_NO, FAQ_FAQTNO, FAQ_TITLE, FAQ_CONTENT, FAQ_STAT, FAQ_ADMINISNO)
VALUES('FAQ' || LPAD(SEQ_FAQNO.NEXTVAL, 7, '0'), 'FAQT000005', 
'在路營區迷路怎麼辦？', 
'依各業者規定所示。',
0, 'A000000001');

INSERT INTO FAQ
(FAQ_NO, FAQ_FAQTNO, FAQ_TITLE, FAQ_CONTENT, FAQ_STAT, FAQ_ADMINISNO)
VALUES('FAQ' || LPAD(SEQ_FAQNO.NEXTVAL, 7, '0'), 'FAQT000005', 
'不小心轉生到異世界怎麼辦？', 
'首先你要確認身邊能不能帶女神，如果不行的話別擔心大部分場合都會有主角優勢，但少數例外的情況...',
0, 'A000000001');

INSERT INTO FAQ
(FAQ_NO, FAQ_FAQTNO, FAQ_TITLE, FAQ_CONTENT, FAQ_STAT, FAQ_ADMINISNO)
VALUES('FAQ' || LPAD(SEQ_FAQNO.NEXTVAL, 7, '0'), 'FAQT000005', 
'遇到熊怎麼辦？', 
'可以跟熊一起拍照，然後打卡。',
0, 'A000000001');

------------------------------------------------

DROP TABLE ACTIVITY;
DROP SEQUENCE SEQ_ACTNO;

CREATE SEQUENCE SEQ_ACTNO
MAXVALUE 9999999
NOCYCLE
NOCACHE;


CREATE TABLE ACTIVITY (
    ACT_NO CHAR(10) PRIMARY KEY NOT NULL,
    ACT_TITLE VARCHAR2(30 CHAR) NOT NULL,
    ACT_CONTENT VARCHAR2(1000 CHAR),
    ACT_EDIT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ACT_STAT NUMBER(1) DEFAULT 0 NOT NULL,
    ACT_ADMINISNO CHAR(10 CHAR),
    CONSTRAINT FK_ACTIVITY_ADMINIS
    FOREIGN KEY(ACT_ADMINISNO) REFERENCES ADMINIS(ADMINIS_NO)
);

INSERT INTO ACTIVITY
(ACT_NO, ACT_TITLE, ACT_CONTENT, ACT_STAT, ACT_ADMINISNO)
VALUES('ACT' || LPAD(SEQ_ACTNO.NEXTVAL, 7, '0'), 
'【肝滅のPLAMPING - 無限購物車篇】現正熱映中！！！', 
'「我會履行我的職責，不會讓在場的任何人被當掉！！  ─ 必安柱‧大衛」<br>
<br>
由不知名專題製作小組「EA‧103G8社」嘔心瀝血打造，2020年度大作【肝滅のPLAMPING - 無限購物車篇】，
現正於中央大學工程二館火熱上映中，上映短短不到一周票房已超過60+人，網友看完紛紛表示超級感動。<br>
「超哭的啦，看到最後訂單成功送出到後端那一刻，我都不知道用掉幾包衛生紙了。」<br>
「很熱血也很感動，能感受到製作組的血淚，看完都想進蟲殺隊殺BUG了。」<br>
「新垣結衣我老婆啦，不服來戰！！」<br>
「必安柱一邊喊著『摸斗哈壓哭』一邊抽出第二支鍵盤殺BUG那幕，真的超帥的！」<br>
對於網友的盛讚，製作組不願意具名的陳姓組長謙虛的表示「作品還有進步的空間，今後會更精進寫扣，帶給大家更好的作品。」<br>
而製作組其他組員也要呼籲大家，雖然炎柱最後會犧牲，但要顧及還未觀賞的朋友，切勿有任何劇透的行為。<br>
本作品人氣仍在不斷飆升，沒跟上風潮的朋友，還不趕快衝一波！
<br>
時間：2020年11月12號<br>
地點：中央大學工程二館<br>
費用：無料',
2, 'A000000007');

INSERT INTO ACTIVITY
(ACT_NO, ACT_TITLE, ACT_CONTENT, ACT_STAT, ACT_ADMINISNO)
VALUES('ACT' || LPAD(SEQ_ACTNO.NEXTVAL, 7, '0'),
'Δ搖曳露營第二季即將開播，富士本栖湖露營體驗團搶先開跑囉！！', 
'時間：2020年12月1日~2020年12月15日<br>
行程：<br>
12月1日  -> 抵達日本，東急飯店開始居家檢疫。<br>
12月2日  -> 東急飯店居家檢疫<br>
12月3日  -> 東急飯店居家檢疫<br>
12月4日  -> 東急飯店居家檢疫<br>
12月5日  -> 東急飯店居家檢疫<br>
12月6日  -> 東急飯店居家檢疫<br>
12月7日  -> 東急飯店居家檢疫<br>
12月8日  -> 東急飯店居家檢疫<br>
12月9日  -> 東急飯店居家檢疫<br>
12月10日 -> 東急飯店居家檢疫<br>
12月11日 -> 東急飯店居家檢疫<br>
12月12日 -> 東急飯店居家檢疫<br>
12月13日 -> 東急飯店居家檢疫<br>
12月14日 -> 東急飯店居家檢疫<br>
12月15日 -> 富士本栖湖一日遊<br>
12月16日 -> 滿載而歸~<br>
費用：每人新台幣20萬元<br>
11月15日報名截止，名額有限，要搶要快！', 
2, 'A000000007');

INSERT INTO ACTIVITY
(ACT_NO, ACT_TITLE, ACT_CONTENT, ACT_STAT, ACT_ADMINISNO)
VALUES('ACT' || LPAD(SEQ_ACTNO.NEXTVAL, 7, '0'), 
'日本絕美秘境「安達與島村」 - 在百合花海中浪漫露營', 
'坐落於於深山的百合花海當中，連日本人都鮮少知道的秘境村落「安達與島村」，<br>
受到氣候異常的影響，這個冬季迎來本世紀最美的百合花大盛開，村民為推展光觀，<br>
將於12月起盛大舉辦「百合露營祭」，有興趣的朋友歡迎前往共「香」盛舉。<br>
時間：12月1日~31日<br>
地點：日本 安達與島村<br>
費用：每人新台幣1萬元起',
2, 'A000000007');

INSERT INTO ACTIVITY
(ACT_NO, ACT_TITLE, ACT_CONTENT, ACT_STAT, ACT_ADMINISNO)
VALUES('ACT' || LPAD(SEQ_ACTNO.NEXTVAL, 7, '0'), 
'宜蘭東澳獨木舟野營，報名即將截止，要搶要快', 
'時間：2020年11月12號、13號<br>
地點：宜蘭東澳<br>
費用：4人一帳，每人單價 TWD 3,500 起',
2, 'A000000007');

INSERT INTO ACTIVITY
(ACT_NO, ACT_TITLE, ACT_CONTENT, ACT_STAT, ACT_ADMINISNO)
VALUES('ACT' || LPAD(SEQ_ACTNO.NEXTVAL, 7, '0'),
'~浪漫星空、絕美日出~ 清水斷崖日出獨木舟露營', 
'時間：2020年11月20日~21日<br>
地點：花蓮秀林崇德海灘<br>
費用：4人一帳，每人單價	 TWD 3,150 起', 
2, 'A000000007');

INSERT INTO ACTIVITY
(ACT_NO, ACT_TITLE, ACT_CONTENT, ACT_STAT, ACT_ADMINISNO)
VALUES('ACT' || LPAD(SEQ_ACTNO.NEXTVAL, 7, '0'), 
'枕頭山星空體驗體驗營，喜愛睡覺的朋友快來參加~', 
'時間：2021/1/1<br>
地點：枕頭山<br>
費用：每人新台幣5000元', 
2, 'A000000007');

INSERT INTO ACTIVITY
(ACT_NO, ACT_TITLE, ACT_CONTENT, ACT_STAT, ACT_ADMINISNO)
VALUES('ACT' || LPAD(SEQ_ACTNO.NEXTVAL, 7, '0'),
'城市野營嘉年華，揪你一起戶外找生活', 
'活動時間：2021/1/24 11:00~21:00<br>
活動地點：台北市華中露營場 (台北市萬華區萬大路底 華中河濱公園左區)<br>
活動費用：全場區免費入場、風格露營包廂付費訂位入住',
2, 'A000000008');

INSERT INTO ACTIVITY
(ACT_NO, ACT_TITLE, ACT_CONTENT, ACT_STAT, ACT_ADMINISNO)
VALUES('ACT' || LPAD(SEQ_ACTNO.NEXTVAL, 7, '0'), 
'澎湖無人島SUP星空露營', 
'時間：2021/1/24 14：00<br>
地點：澎湖馬公市區南海碼頭報到<br>
費用：每人新台幣4000元', 
2, 'A000000008');

INSERT INTO ACTIVITY
(ACT_NO, ACT_TITLE, ACT_CONTENT, ACT_STAT, ACT_ADMINISNO)
VALUES('ACT' || LPAD(SEQ_ACTNO.NEXTVAL, 7, '0'), 
'南投竹山「山思雲想」豪華露營｜森活美學', 
'時間：2021/2/28<br>
地點：南投竹山山思雲想露營區<br>
費用：7人一帳,每人單價 TWD 3,800 起', 
2, 'A000000008');

INSERT INTO ACTIVITY
(ACT_NO, ACT_TITLE, ACT_CONTENT, ACT_STAT, ACT_ADMINISNO)
VALUES('ACT' || LPAD(SEQ_ACTNO.NEXTVAL, 7, '0'), 
'宜蘭東風夢幻露營Glamping｜免裝備．職感生活', 
'時間：2021/3/10<br>
地點：宜蘭東風夢幻營區<br>
費用：5人一帳，每人單價	 TWD 3,500 起', 
2, 'A000000008');

INSERT INTO ACTIVITY
(ACT_NO, ACT_TITLE, ACT_CONTENT, ACT_STAT, ACT_ADMINISNO)
VALUES('ACT' || LPAD(SEQ_ACTNO.NEXTVAL, 7, '0'), 
'宜蘭那山那谷免裝備露營', 
'時間：2021/4/24<br>
地點：宜蘭那山那谷營區<br>
費用：2~4人一帳，每帳單價 TWD 9,900 起', 
2, 'A000000009');

INSERT INTO ACTIVITY
(ACT_NO, ACT_TITLE, ACT_CONTENT, ACT_STAT, ACT_ADMINISNO)
VALUES('ACT' || LPAD(SEQ_ACTNO.NEXTVAL, 7, '0'), 
'苗栗黃金梯田．免裝備風格露營', 
'時間：2021/5/20<br>
地點：苗栗黃金梯田露營區<br>
費用：豪華鐘型帳（6~8人），每帳單價 TWD 14,500 起', 
2, 'A000000009');

------------------------------------------------

DROP TABLE EQPT_INTRO;
DROP SEQUENCE SEQ_EINO;

CREATE SEQUENCE SEQ_EINO
MAXVALUE 9999999
NOCYCLE
NOCACHE;


CREATE TABLE EQPT_INTRO (
  EI_NO CHAR(10) PRIMARY KEY NOT NULL,
  EI_TITLE VARCHAR2(30 CHAR) NOT NULL,
  EI_ADMINISNO CHAR(10) NOT NULL,
  EI_CONTENT VARCHAR2(300 CHAR) NOT NULL,
  EI_EDIT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  EI_STAT NUMBER(1) DEFAULT 0 NOT NULL,
  CONSTRAINT FK_EQPTINTRO_ADMINIS
  FOREIGN KEY(EI_ADMINISNO) REFERENCES ADMINIS(ADMINIS_NO)
);

INSERT INTO EQPT_INTRO
(EI_NO, EI_TITLE, EI_CONTENT, EI_STAT, EI_ADMINISNO)
VALUES('EI' || LPAD(SEQ_EINO.NEXTVAL, 8, '0'),
'【兩秒帳】如何快速搭建、收納？快跟繁瑣露營過程說掰掰！', 
'1. 帳篷內部有著一個橘紅色的帶子，附有一塑膠公母扣，往帳篷門外斜對角拉出，有個對應顏色的扣榫，將同樣顏色的公母扣鎖上。<br>
2. 在帳篷另一面你會找到黃色的塑膠公母扣環，進行同上的步驟。<br>
3. 現在你在前方可找到一個附有紅色帶子的拉繩。從邊緣處將帳篷立起。從紅帶子的扣環繩子，另一手使力按壓帳篷所形成的圓形邊上，另一手從扣環處將它往上拉。<br>
4. 原本的圓環變成一個 8 的形狀。<br>
5. 將 8 字形狀的環結固定在地面，同時再對折一次形成數字零的圈圈。此時會看到管子上的紅色標記處，再將它們彼此對齊。<br>
6. 然後再用黃色帶子固定整個帳篷。', 
2, 'A000000002');

INSERT INTO EQPT_INTRO
(EI_NO, EI_TITLE, EI_CONTENT, EI_STAT, EI_ADMINISNO)
VALUES('EI' || LPAD(SEQ_EINO.NEXTVAL, 8, '0'), '【yamaha vino 50】長征露途好夥伴', 
'目前為台灣山葉機車旗下唯一50cc等級的輕型速克達，Vino問世多年，以復古造型與靈巧操控著名。<br>
經過多年精進，Vino搭載原裝進口日本元件的電子噴射系統與3V水冷引擎，使得vino 50 Fi不僅加速順暢而且環保省油。鑽錶裝飾概念所設計的碼錶搭配白色的面板，讓騎士不只可以清楚辨識速度以及各項燈號，更提升優雅清新都會感。<br>
是您搖曳露營的好夥伴。', 
2, 'A000000002');

INSERT INTO EQPT_INTRO
(EI_NO, EI_TITLE, EI_CONTENT, EI_STAT, EI_ADMINISNO)
VALUES('EI' || LPAD(SEQ_EINO.NEXTVAL, 8, '0'), 
'【天幕】到底要如何搭？要搭也要搭得好看漂亮！', 
' 在搭設天幕帳前，需了解營地的方位，先將指北針平放於地面，紅色的針寫上Ｎ的對準表上Ｎ的位置，代表那邊的方向為北方，接著只要將天幕搭成屋式的方向，再搭配上天幕的帷幕布，擋掉陽光，您的天幕將能達大最大的遮陽面積。<br>
黃昏時，一般我的帳篷會放置的天幕的西邊，並多使用一支營柱將天幕再加高，因為到夜晚時的濕度會被天幕擋掉，在天幕下的草皮、帳篷的外帳、與桌椅爐具就會減少水氣，除了增加了露營的舒適度外，也會增長露營裝備的壽命。<br>', 
2, 'A000000002');

INSERT INTO EQPT_INTRO
(EI_NO, EI_TITLE, EI_CONTENT, EI_STAT, EI_ADMINISNO)
VALUES('EI' || LPAD(SEQ_EINO.NEXTVAL, 8, '0'), 
'【露營用店暖爐】，冬天也要暖暖的，小巧攜帶好方便', 
'冬天來了，你是否因為害怕晚上冷得睡不著，而放棄出門露營呢？不妨帶個電暖爐出發吧。
◎產品特點：<br>
1.整機使用防火阻燃材質生產 <br>
2.溫控超溫斷電，產品內部溫度上昇至70度以上，立即斷電停止運轉<br>
3.電子防傾倒斷熱，水平偏離45度，立即停止加熱，確保安全 <br>
4.可調式導風口設計，熱風吹送更集中，減少熱散逸情況', 
2, 'A000000002');

INSERT INTO EQPT_INTRO
(EI_NO, EI_TITLE, EI_CONTENT, EI_STAT, EI_ADMINISNO)
VALUES('EI' || LPAD(SEQ_EINO.NEXTVAL, 8, '0'), 
'【焚火台】升一盞火，溫暖人心', 
'老實說焚火台，是個人化的需求，是露營中，因為個人的偏好與喜好所購買的裝備之一。雖說它並不是露營中的必備品，不過它確是一個好物。<br>
它除了設計簡潔，搭收方便外，也可燒柴升營火，在營火晚會中，不會破壞到大自然的草皮，具有保護草皮的作用，亦可帶來營火晚會快樂又難忘的美好回憶。而且焚火台除了能烤肉外，亦是荷蘭鍋炊煮時的好伙伴，在冷呼呼的冬天裡它還扮演著暖爐的角色，所以是一個，個人偏好裝備的好物之一。<br>', 
2, 'A000000002');

INSERT INTO EQPT_INTRO
(EI_NO, EI_TITLE, EI_CONTENT, EI_STAT, EI_ADMINISNO)
VALUES('EI' || LPAD(SEQ_EINO.NEXTVAL, 8, '0'),
'【露營椅怎麼選?】各種型式露營椅的優缺點分析', 
'挑選露營椅的三大重點 : 椅子高低、靠背型式、收納尺寸及型式。除此之外更多需要注意的重點，舉列如下：<br>
組裝難易度<br>
組裝難易度在露營椅好像比較少被討論，因為通常都很容易。但有一種最近蠻流行的「輕量露營椅」，組裝上就比較麻煩，但好處就是它收納後的體積非常小，而且很輕巧，缺點就是穩定性較差一點。<br>
場地(域)大小<br>
如果你的裝備是較封閉「客廳帳」，那你的室內空間就很有限，不適合擺放大張的露營椅或躺椅。但如果你的裝備是較開放的「天幕」，那就有條件去選擇較大的椅子，坐起來較舒服。<br>', 
2, 'A000000003');

INSERT INTO EQPT_INTRO
(EI_NO, EI_TITLE, EI_CONTENT, EI_STAT, EI_ADMINISNO)
VALUES('EI' || LPAD(SEQ_EINO.NEXTVAL, 8, '0'),
'【羽絨睡袋】職人教你如何挑選', 
'羽絨睡袋優缺點<br>
優點: 保暖性佳、蓬鬆又輕巧、可擠壓且收納體積小，非常適合徒步登山或長天數攜帶。填充物主要來自鵝毛及鴨毛，鵝絨比鴨絨成本還要貴，但好的鴨絨就非常保暖了。<br>
不過有些廠商與品牌會混入其他禽類羽毛（比羽絨粗梗）或合成纖維來假冒降低成本、屢試不爽!因此、一般製造商都應該在羽絨製品上標示填充物的種類、比例、來源地、認證規範，購買時要特別注意。(沒標示請三思) <br>
<br>
缺點: 羽絨的缺點是價格昂貴、品質良莠不齊、保養不易、多數都怕水與高濕度，一旦受潮濕掉後羽絨會散不開，失去保暖功效甚至腐敗導致損壞。<br>', 
2, 'A000000003');

INSERT INTO EQPT_INTRO
(EI_NO, EI_TITLE, EI_CONTENT, EI_STAT, EI_ADMINISNO)
VALUES('EI' || LPAD(SEQ_EINO.NEXTVAL, 8, '0'), 
'【蛋捲桌】合進行多種活動時使用', 
'輕巧的蛋捲桌，可摺疊、可調整高度且方便攜帶，適合 2 到 4 人聊天、享用零食使用。', 
2, 'A000000003');

INSERT INTO EQPT_INTRO
(EI_NO, EI_TITLE, EI_CONTENT, EI_STAT, EI_ADMINISNO)
VALUES('EI' || LPAD(SEQ_EINO.NEXTVAL, 8, '0'),
'【充氣床墊、充氣枕頭】讓您睡得更加舒適', 
'不含PVC 塑膠（聚氯乙烯）的充氣床墊。睡墊表面類似獨立筒設計，更加舒適。<br>
充氣枕頭重量只有170 g，是一個非常輕量、輕巧及超級舒適的枕頭。且參照保養建議，就能輕鬆地使用洗衣機清洗。', 
2, 'A000000003');

INSERT INTO EQPT_INTRO
(EI_NO, EI_TITLE, EI_CONTENT, EI_STAT, EI_ADMINISNO)
VALUES('EI' || LPAD(SEQ_EINO.NEXTVAL, 8, '0'),
'【帳篷防潮地墊】為帳篷提供額外的防護', 
'放在帳篷外底部，為帳篷提供額外的防護層，可以防止磨損和地面上石頭與樹根的穿刺，延長帳篷壽命；如果遇到下雨天，更讓帳篷不怕雨天的地面泥水。防水材質清洗容易。', 
2, 'A000000003');

------------------------------------------------

DROP TABLE GUIDE;
DROP SEQUENCE SEQ_GUIDENO;

CREATE SEQUENCE SEQ_GUIDENO
MAXVALUE 9999999
NOCYCLE
NOCACHE;


CREATE TABLE GUIDE (
    GUIDE_NO CHAR(10) PRIMARY KEY NOT NULL,
	GUIDE_TITLE VARCHAR2(30 CHAR),
    GUIDE_CONTENT VARCHAR2(300 CHAR),
    GUIDE_EDIT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    GUIDE_STAT NUMBER(1) DEFAULT 0 NOT NULL ,
    GUIDE_ADMINISNO CHAR(10) NOT NULL,
    CONSTRAINT FK_GUIDE_ADMINIS
    FOREIGN KEY(GUIDE_ADMINISNO) REFERENCES ADMINIS(ADMINIS_NO)
);


INSERT INTO GUIDE
(GUIDE_NO, GUIDE_TITLE, GUIDE_CONTENT, GUIDE_STAT, GUIDE_ADMINISNO)
VALUES('GD' || LPAD(SEQ_GUIDENO.NEXTVAL, 8, '0'), 
'【新手露營】如何打理你的露營裝備？', 
'關於RV露營，新手要打理裝備的原則其實很簡單，那就是「家裡能用的就用、能借的就不要買」。一開始就澆熄你想要購買裝備的慾望，其實真的只是希望你先體驗露營並了解露營，在經過一次、兩次之後，評估自己真的喜歡露營且願意花錢投資裝備時，再來考量進階和升級。<br>
出門露營要準備的東西很多，主要目的是可以提高露營的舒適度和方便性。基本的露營裝備，包含帳篷類、工具類、寢具類、煮食類、燈具類，其他還有桌椅、衣物、個人盥洗用品、食材等。不過露得越久、裝備也會越來越簡化和輕量化，畢竟花在裝備上的時間越多，享受自然風景的時間就越少。接下來我們會依逐項說明露營裝備前期及後期的準備與選擇。<br>
詳全文...', 
2, 'A000000001');

INSERT INTO GUIDE
(GUIDE_NO, GUIDE_TITLE, GUIDE_CONTENT, GUIDE_STAT, GUIDE_ADMINISNO)
VALUES('GD' || LPAD(SEQ_GUIDENO.NEXTVAL, 8, '0'), 
'【新手露營攻略】10大新手常見問題，讓你輕鬆展開露營之旅', 
'今天就來整理一篇「新手露營10大常見問題」，例如：睡的空間、休閒的空間、準備甚麼食物…，希望可以讓大家更了解露營。<br>
10大新手露營常見問題<br>
1.如何挑選我的帳篷？<br>
2.睡覺怎樣可以更舒適？<br>
3.吃飯怎麼準備？食材如何保鮮？<br>
4.可以洗澡嗎？要注意些甚麼？<br>
5.怎麼找適合的營區？<br>
6.露營用電有甚麼需要注意？<br>
7.垃圾怎麼處理？<br>
8.下雨天還能露營嗎？<br>
9.有哪些「必備」裝備？<br>
10.沒有裝備，也可以體驗露營嗎？<br>
詳全文...', 
2, 'A000000001');

INSERT INTO GUIDE
(GUIDE_NO, GUIDE_TITLE, GUIDE_CONTENT, GUIDE_STAT, GUIDE_ADMINISNO)
VALUES('GD' || LPAD(SEQ_GUIDENO.NEXTVAL, 8, '0'),
'【露營攻略】懶人包。營地推薦、裝備開箱、國內外露營經驗', 
'為了讓大家可以更輕鬆快速找到想要的露營資訊，花點時間以露營裝備、露營裝備規格分析、裝備開箱、露營經驗分享、台灣營地分享、國外露營分享到露友QA時間為分類，彙整出這篇露營攻略懶人包，完整的記錄了我們在台灣及歐洲北美的露營經驗，相信看完之後都能開心的露營趣囉！<br>
詳全文...', 
2, 'A000000001');

INSERT INTO GUIDE
(GUIDE_NO, GUIDE_TITLE, GUIDE_CONTENT, GUIDE_STAT, GUIDE_ADMINISNO)
VALUES('GD' || LPAD(SEQ_GUIDENO.NEXTVAL, 8, '0'), 
'【露營用品清單】露營該帶哪些東西?', 
'開始想露營，又不想一開始就花大錢，我應該優先購買哪些裝備呢？<br>
整理露備的時候，不確定要帶哪些東西？不確定什麼是必需品？<br>
我們就來幫大家整理一份「露營裝備清單」吧！<br>
1.項露營用品 / 裝備清單<br>
2.帳篷/客廳帳<br>
3.客廳 / 廚房用具<br>
4.料理用具<br>
5.照明燈具<br>
6.帳內用具<br>
7.其他<br>
詳全文...', 
2, 'A000000001');

INSERT INTO GUIDE
(GUIDE_NO, GUIDE_TITLE, GUIDE_CONTENT, GUIDE_STAT, GUIDE_ADMINISNO)
VALUES('GD' || LPAD(SEQ_GUIDENO.NEXTVAL, 8, '0'),
'【露營教學】家庭露營必學的5大技巧，紮營、選地點的注意事項', 
'最近，我帶了很多朋友來體驗露營，有些人甚至就開始買帳篷、買裝備，一腳踏進了露營這個坑。<br>
當我帶了這麼多朋友露營之後，慢慢地整理出一些大家在搭帳、紮營時常會碰到的問題，不一定是初露的朋友，有些可能已經露營很多次了，但還是會碰到同樣問題。<br>
如果你正準備規畫你的「初露」(第一次露營)，或者你是剛開始露營的初心者，這篇文章將會對你很有幫助！<br>
5個重要紮營技巧/注意事項<br>
1. 紮營的位置，如何選擇？<br>
2. 下釘有哪些技巧？<br>
3. 下雨天的注意事項<br>
4. 如何保持通風，避免返潮<br>？
5. 其他技巧/注意事項<br>
詳全文...', 
2, 'A000000001');

INSERT INTO GUIDE
(GUIDE_NO, GUIDE_TITLE, GUIDE_CONTENT, GUIDE_STAT, GUIDE_ADMINISNO)
VALUES('GD' || LPAD(SEQ_GUIDENO.NEXTVAL, 8, '0'), 
'【輕鬆談露營】新手露營兩三事！露營裝備、注意事項、行前準備', 
'小時候跟著爸媽露營是以裝備不足的克難方式進行，晚上需忍受悶熱睡在凹凸不平的帳內仍記憶猶新。而這天，我們終於有充分準備來迎接一次真正的露營了！<br>
由於需要準備的東西不少深怕遺漏，這邊做個紀錄日後方便查詢，也順便和大家淺談我們的第二次露營的心得<br>
詳全文...', 
2, 'A000000001');

COMMIT;

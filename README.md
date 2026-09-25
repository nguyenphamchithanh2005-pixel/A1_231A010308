Bài nâng cao 1 (NC1): Đếm số lần gọi mỗi callback
Mô tả: Dùng một LinkedHashMap<String, Integer> để đếm số lần mỗi callback vòng đời được gọi, hiển thị dạng "onResume: 3 | onPause: 2 | ..." trên một TextView riêng (tvCounter), cập nhật lại mỗi lần logEvent() được gọi.

Bài nâng cao 2 (NC2): Bắt lỗi NullPointerException, hiện Toast thay vì crash
Mô tả: Nút "Gây lỗi" trước đây khiến app crash do gọi .length() trên biến String null. Đã bọc thao tác này trong try/catch để bắt NullPointerException, ghi log bằng Log.e và hiển thị Toast thông báo cho người dùng thay vì để ứng dụng bị dừng đột ngột.

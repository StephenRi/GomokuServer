package message;

public class StepMessage {
    private String userId;
    private String roomNo;
    private String userRole;
    private int chessX;
    private int chessY;
    private boolean isOver;
    private String turn;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public int getChessX() {
        return chessX;
    }

    public void setChessX(int chessX) {
        this.chessX = chessX;
    }

    public int getChessY() {
        return chessY;
    }

    public void setChessY(int chessY) {
        this.chessY = chessY;
    }

    public boolean getIsOver() {
        return isOver;
    }

    public void setIsOver(boolean isOver) {
        this.isOver = isOver;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }
}

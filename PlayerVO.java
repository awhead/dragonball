public class PlayerVO {

    private String playerName;
    private int action;
    private boolean isPlayerBot;
    private int kiCount;
    private int hp;
    // hp 변동 확인
    private boolean hpStatus;
    // 공격을 했는지 확인
    private boolean attackStatus;
    // 기를 모았는지 확인
    private boolean kiStatus;

    PlayerVO(boolean isPlayerBot, String playerName, int hp) {
	this.isPlayerBot = isPlayerBot;
	this.playerName = playerName;
	this.hp = hp;
    }

    public String getPlayerName() {
	return playerName;
    }

    public int getAction() {
	return action;
    }

    public boolean isPlayerBot() {
	return isPlayerBot;
    }

    public int getKiCount() {
	return kiCount;
    }

    public int getHp() {
	return hp;
    }

    public boolean isHpStatus() {
	return hpStatus;
    }

    public boolean isAttackStatus() {
	return attackStatus;
    }

    private boolean isKiStatus() {
	return kiStatus;
    }

    private void setPlayerName(String playerName) {
	this.playerName = playerName;
    }

    public void setAction(int action) {
	this.action = action;
    }

    private void setIsPlayerBot(boolean playerIsBot) {
	this.isPlayerBot = playerIsBot;
    }

    public void setKiCount(int kiCount) {
	this.kiCount = kiCount;
    }

    public void setHp(int hp) {
	this.hp = hp;
    }

    public void setHpStatus(boolean hpStatus) {
	this.hpStatus = hpStatus;
    }

    private void setAttackStatus(boolean attackStatus) {
	this.attackStatus = attackStatus;
    }

    private void setKiStatus(boolean kiStatus) {
	this.kiStatus = kiStatus;
    }

    @Override
    public String toString() {
	String actionTemp = null;
	switch (action) {
	case 1:
	    actionTemp = "기모으기";
	    break;
	case 2:
	    actionTemp = "막기";
	    break;
	case 3:
	    actionTemp = "공격";
	    break;

	}

	return playerName + "의 ##" + actionTemp + "##\n[hp=     " + hp + " || 기=     " + kiCount + "\t]";
    }

}

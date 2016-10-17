package com.ludong.model;

public class GyroscopeInfo {
   
    public boolean isNC0() {
		return NC0;
	}
	public void setNC0(boolean nC0) {
		NC0 = nC0;
	}
	public boolean isNC1() {
		return NC1;
	}
	public void setNC1(boolean nC1) {
		NC1 = nC1;
	}
	public boolean isTurnRight() {
		return TurnRight;
	}
	public void setTurnRight(boolean turnRight) {
		TurnRight = turnRight;
	}
	public boolean isCalibration() {
		return Calibration;
	}
	public void setCalibration(boolean calibration) {
		Calibration = calibration;
	}
	public boolean isStaticRollover() {
		return StaticRollover;
	}
	public void setStaticRollover(boolean staticRollover) {
		StaticRollover = staticRollover;
	}
	public boolean isDynamicRollover() {
		return DynamicRollover;
	}
	public void setDynamicRollover(boolean dynamicRollover) {
		DynamicRollover = dynamicRollover;
	}
	public boolean isBumpyRoad() {
		return BumpyRoad;
	}
	public void setBumpyRoad(boolean bumpyRoad) {
		BumpyRoad = bumpyRoad;
	}
	public boolean isTurnLeft() {
		return TurnLeft;
	}
	public void setTurnLeft(boolean turnLeft) {
		TurnLeft = turnLeft;
	}
	public boolean isSlowBeforeTurn() {
		return SlowBeforeTurn;
	}
	public void setSlowBeforeTurn(boolean slowBeforeTurn) {
		SlowBeforeTurn = slowBeforeTurn;
	}
	public boolean isDownhillAcceleration() {
		return DownhillAcceleration;
	}
	public void setDownhillAcceleration(boolean downhillAcceleration) {
		DownhillAcceleration = downhillAcceleration;
	}
	public boolean isDownhillSlowDown() {
		return DownhillSlowDown;
	}
	public void setDownhillSlowDown(boolean downhillSlowDown) {
		DownhillSlowDown = downhillSlowDown;
	}
	public boolean isBrakeHard() {
		return BrakeHard;
	}
	public void setBrakeHard(boolean brakeHard) {
		BrakeHard = brakeHard;
	}
	public boolean isDownTurn() {
		return DownTurn;
	}
	public void setDownTurn(boolean downTurn) {
		DownTurn = downTurn;
	}
	public boolean isSRouteCycling() {
		return SRouteCycling;
	}
	public void setSRouteCycling(boolean sRouteCycling) {
		SRouteCycling = sRouteCycling;
	}
	public boolean isSlewHard() {
		return SlewHard;
	}
	public void setSlewHard(boolean slewHard) {
		SlewHard = slewHard;
	}
	public boolean isNightOffLight() {
		return NightOffLight;
	}
	public void setNightOffLight(boolean nightOffLight) {
		NightOffLight = nightOffLight;
	}
	public float getXAngle() {
		return XAngle;
	}
	public void setXAngle(float xAngle) {
		XAngle = xAngle;
	}
	public float getYAngle() {
		return YAngle;
	}
	public void setYAngle(float yAngle) {
		YAngle = yAngle;
	}
	public float getZAngle() {
		return ZAngle;
	}
	public void setZAngle(float zAngle) {
		ZAngle = zAngle;
	}
	public float getXAcceleratedSpeed() {
		return XAcceleratedSpeed;
	}
	public void setXAcceleratedSpeed(float xAcceleratedSpeed) {
		XAcceleratedSpeed = xAcceleratedSpeed;
	}
	public float getYAcceleratedSpeed() {
		return YAcceleratedSpeed;
	}
	public void setYAcceleratedSpeed(float yAcceleratedSpeed) {
		YAcceleratedSpeed = yAcceleratedSpeed;
	}
	public float getZAcceleratedSpeed() {
		return ZAcceleratedSpeed;
	}
	public void setZAcceleratedSpeed(float zAcceleratedSpeed) {
		ZAcceleratedSpeed = zAcceleratedSpeed;
	}
	
	boolean NC0;
	boolean NC1;
    boolean TurnRight;
    boolean Calibration;//У׼
    boolean StaticRollover;
    boolean DynamicRollover;
    boolean BumpyRoad;
    boolean TurnLeft;
    boolean SlowBeforeTurn;
    boolean DownhillAcceleration;
    boolean DownhillSlowDown;
    boolean BrakeHard;
    boolean DownTurn;
    boolean SRouteCycling;
    boolean SlewHard;
    boolean NightOffLight;
    float XAngle;
    float YAngle;
    float ZAngle;
    float XAcceleratedSpeed;
    float YAcceleratedSpeed;
    float ZAcceleratedSpeed;
    private long SamplingTime;
	private long WrittenIntoSystemTime;
	private float Version=0;
	private long TerminalID=0;
	
	public long getSamplingTime() {
		return SamplingTime;
	}
	public void setSamplingTime(long samplingTime) {
		SamplingTime = samplingTime;
	}
	public long getWrittenIntoSystemTime() {
		return WrittenIntoSystemTime;
	}
	public void setWrittenIntoSystemTime(long writtenIntoSystemTime) {
		WrittenIntoSystemTime = writtenIntoSystemTime;
	}
	public float getVersion() {
		return Version;
	}
	public void setVersion(float version) {
		Version = version;
	}
	public long getTerminalID() {
		return TerminalID;
	}
	public void setTerminalID(long terminalID) {
		TerminalID = terminalID;
	}
}

package com.ludong.model;

import java.util.Date;

public class ControllerInfo {
	
	private long TerminalID = 0;
	private int VIN = 0;
	private float RideVoltage = 0;
    private float RideCurrent = 0;
    private float Temperature = 0;
    private boolean IsRunning=false;
    private boolean IsUnderLimitVoltage = false;
    private boolean IsOverTemperature = false;
    private boolean IsOverLoad = false;
    private boolean IsBrakeHandle = false; //刹把
    private boolean IsTurnHandle = false;
    private boolean IsTrumpet = false; //喇叭
    private boolean IsHeadLightOn = false;
    private boolean IsTurnLeftLightOn = false;
    private boolean IsTurnRightLightOn = false;
    private boolean IsMotorFault = false;
    private boolean IsControllerFault = false;
    private boolean IsTurnHandleFault = false;
    private boolean IsBrakeHandleFault = false;
    private long SamplingTime=0;
    private long WrittenIntoSystemTime=0;
    private boolean IsRealTme=true;
    private float Version=0;
 
    
    public float GetVersion() {
        return Version;
    }
    public void SetVersion(float version) {
        this.Version=version;
     }
    
    public boolean GetIsRealTime() {
        return IsRealTme;
    }
    public void SetIsRealTime(boolean isRealTime) {
        this.IsRealTme=isRealTime;
     }
    public boolean GetIsBrakeHandleFault() {
        return IsBrakeHandleFault;
    }
    public void SetIsBrakeHandleFault(boolean isBrakeHandleFault) {
       this.IsBrakeHandleFault=isBrakeHandleFault;
    }
    
    public boolean GetIsTurnHandleFault() {
        return IsTurnHandleFault;
    }
    public void SetIsTurnHandleFault(boolean isTurnHandleFault) {
       this.IsTurnHandleFault=isTurnHandleFault;
    }
    
    public boolean GetIsControllerFault() {
        return IsControllerFault;
    }
    public void SetIsControllerFault(boolean isControllerFault) {
       this.IsControllerFault=isControllerFault;
    }
    
    
    public boolean GetIsMotorFault() {
        return IsMotorFault;
    }
    public void SetIsMotorFault(boolean isMotorFault) {
       this.IsMotorFault=isMotorFault;
    }
    
    public boolean GetIsTurnRightLightOn() {
        return IsTurnRightLightOn;
    }
    public void SetIsTurnRightLightOn(boolean isTurnRightLightOn) {
       this.IsTurnRightLightOn=isTurnRightLightOn;
    }
    
    public boolean GetIsTurnLeftLightOn() {
        return IsTurnLeftLightOn;
    }
    public void SetIsTurnLeftLightOn(boolean isTurnLeftLightOn) {
       this.IsTurnLeftLightOn=isTurnLeftLightOn;
    }
    
    public boolean GetIsHeadLightOn() {
        return IsHeadLightOn;
    }
    public void SetIsHeadLightOn(boolean isHeadLightOn) {
       this.IsHeadLightOn=isHeadLightOn;
    }
    
    public boolean GetIsTrumpet() {
        return IsTrumpet;
    }
    public void SetIsTrumpet(boolean isTrumpet) {
       this.IsTrumpet=isTrumpet;
    }
    
    public boolean GetIsTurnHandle() {
        return IsTurnHandle;
    }
    public void SetIsTurnHandle(boolean isTurnHandle) {
       this.IsTurnHandle=isTurnHandle;
    }
    
    public boolean GetIsBrakeHandle() {
        return IsBrakeHandle;
    }
    public void SetIsBrakeHandle(boolean isBrakeHandle) {
       this.IsBrakeHandle=isBrakeHandle;
    }
    
    public boolean GetIsOverLoad() {
        return IsOverLoad;
    }
    public void SetIsOverLoad(boolean isOverLoad) {
       this.IsOverLoad=isOverLoad;
    }
    
    public boolean GetIsOverTemperature() {
        return IsOverTemperature;
    }
    public void SetIsOverTemperature(boolean isOverTemperature) {
       this.IsOverTemperature=isOverTemperature;
    }
    
    public boolean GetIsRunning() {
        return IsRunning;
    }
    public void SetIsRunning(boolean isRunning) {
       this.IsRunning=isRunning;
    }
    
    public boolean GetIsUnderLimitVoltage() {
        return IsUnderLimitVoltage;
    }
    public void SetIsUnderLimitVoltage(boolean isUnderLimitVoltage) {
       this.IsUnderLimitVoltage=isUnderLimitVoltage;
    }
    
    
    public int GetVIN() {
        return VIN;
    }
    public void SetVIN(int vin) {
       this.VIN=vin;
    }
    
    public float GetTemperature() {
        return Temperature;
    }
    public void SetTemperature(float temperature) {
        this.Temperature=temperature;
     }
    
    public float GetRideCurrent() {
        return RideCurrent;
    }
    public void SetRideCurrent(float rideCurrent) {
        this.RideCurrent=rideCurrent;
     }
    
    public float GetRideVoltage() {
        return RideVoltage;
    }
    public void SetRideVoltage(float rideVoltage) {
        this.RideVoltage=rideVoltage;
     }
       
    public long GetTerminalID() {
        return TerminalID;
    }
    public void SetTerminalID(long terminalID) {
       this.TerminalID=terminalID;
    }
    public long GetSamplingTime() {
        return SamplingTime;
    }
    public void SetSamplingTime(long samplingTime) {
       this.SamplingTime=samplingTime;
    }
    public long GetWrittenIntoSystemTime() {
        return WrittenIntoSystemTime;
    }
    public void SetWrittenIntoSystemTime(long writtenIntoSystemTime) {
       this.WrittenIntoSystemTime=writtenIntoSystemTime;
    }
    
    
    
    
    
    
    
    
    //LuYuanHeartInfo
    float TurnHandleScale=0; //转把百分比
    public float GetTurnHandleScale() {
        return TurnHandleScale;
    }
    public void SetTurnHandleScale(float turnHandleScale) {
        this.TurnHandleScale=turnHandleScale;
     }
    
    float Grade=0;//坡度
    public float GetGrade() {
        return Grade;
    }
    public void SetGrade(float grade) {
        this.Grade=grade;
     }
    
    float Speed=0;//速度
    public float GetSpeed() {
        return Speed;
    }
    public void SetSpeed(float speed) {
        this.Speed=speed;
     }
    
    float RideCurrentScale=0;//电流百分比
    public float GetRideCurrentScale() {
        return RideCurrentScale;
    }
    public void SetRideCurrentScale(float rideCurrentScale) {
        this.RideCurrentScale=rideCurrentScale;
     }
    
    float ElectrisityPower=0; //瓦时千米
    public float GetElectrisityPower() {
        return ElectrisityPower;
    }
    public void SetElectrisityPower(float electrisityPower) {
        this.ElectrisityPower=electrisityPower;
     }
    
    boolean IsInRunMode=true;//整车启动

	boolean IsDefence=false;
    boolean IsNightLightOn=false;
    int TransmissionMode=0;//档位模式
    boolean IsCurveLackenSpeed=false; //转弯减速
    boolean  IsOverNormalSpeed=false;
    int ElectricityQuantityScale=0;
    int CyclingEvent=0; //骑行事件 如 翻倒 急刹
    boolean IsMotorPhase=false;//电机缺相
    boolean IsMotorHallFault = false;
    boolean IsLowVoltageProtect = false;
    boolean IsLostCotrolProtect = false;//飞车保护，即失控
    boolean IsOverCurrentProtect = false;
    boolean IsLockedRotorProtect = false;//堵转保护
    //end
    
    public boolean isIsInRunMode() {
		return IsInRunMode;
	}
	public void setIsInRunMode(boolean isInRunMode) {
		IsInRunMode = isInRunMode;
	}
	public boolean isIsDefence() {
		return IsDefence;
	}
	public void setIsDefence(boolean isDefence) {
		IsDefence = isDefence;
	}
	public boolean isIsNightLightOn() {
		return IsNightLightOn;
	}
	public void setIsNightLightOn(boolean isNightLightOn) {
		IsNightLightOn = isNightLightOn;
	}
	public int getTransmissionMode() {
		return TransmissionMode;
	}
	public void setTransmissionMode(int transmissionMode) {
		TransmissionMode = transmissionMode;
	}
	public boolean isIsCurveLackenSpeed() {
		return IsCurveLackenSpeed;
	}
	public void setIsCurveLackenSpeed(boolean isCurveLackenSpeed) {
		IsCurveLackenSpeed = isCurveLackenSpeed;
	}
	public boolean isIsOverNormalSpeed() {
		return IsOverNormalSpeed;
	}
	public void setIsOverNormalSpeed(boolean isOverNormalSpeed) {
		IsOverNormalSpeed = isOverNormalSpeed;
	}
	public int getElectricityQuantityScale() {
		return ElectricityQuantityScale;
	}
	public void setElectricityQuantityScale(int electricityQuantityScale) {
		ElectricityQuantityScale = electricityQuantityScale;
	}
	public int getCyclingEvent() {
		return CyclingEvent;
	}
	public void setCyclingEvent(int cyclingEvent) {
		CyclingEvent = cyclingEvent;
	}
	public boolean isIsMotorPhase() {
		return IsMotorPhase;
	}
	public void setIsMotorPhase(boolean isMotorPhase) {
		IsMotorPhase = isMotorPhase;
	}
	public boolean isIsMotorHallFault() {
		return IsMotorHallFault;
	}
	public void setIsMotorHallFault(boolean isMotorHallFault) {
		IsMotorHallFault = isMotorHallFault;
	}
	public boolean isIsLowVoltageProtect() {
		return IsLowVoltageProtect;
	}
	public void setIsLowVoltageProtect(boolean isLowVoltageProtect) {
		IsLowVoltageProtect = isLowVoltageProtect;
	}
	public boolean isIsLostCotrolProtect() {
		return IsLostCotrolProtect;
	}
	public void setIsLostCotrolProtect(boolean isLostCotrolProtect) {
		IsLostCotrolProtect = isLostCotrolProtect;
	}
	public boolean isIsOverCurrentProtect() {
		return IsOverCurrentProtect;
	}
	public void setIsOverCurrentProtect(boolean isOverCurrentProtect) {
		IsOverCurrentProtect = isOverCurrentProtect;
	}
	public boolean isIsLockedRotorProtect() {
		return IsLockedRotorProtect;
	}
	public void setIsLockedRotorProtect(boolean isLockedRotorProtect) {
		IsLockedRotorProtect = isLockedRotorProtect;
	}








    
}

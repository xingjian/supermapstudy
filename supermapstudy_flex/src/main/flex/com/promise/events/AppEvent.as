package com.promise.events
{
	import flash.events.Event;
	
	public class AppEvent extends Event
	{
		//下面定义事件类型
		public static const LOADXML_ERROR:String = "loadXMLError";
		public static const LOADXML_SUCCESS:String = "loadXMLSuccess";
		
		//事件传递数据对象
		private var data:Object;
		
		public function AppEvent(type:String,data:Object, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
			if(data!=null){
				setData(data);
			}
		}
		
		public function getData():Object{
			return data;
		}
		
		public function setData(data:Object):void{
			this.data = data;
		}
	}
}
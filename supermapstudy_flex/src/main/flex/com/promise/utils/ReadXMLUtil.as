package com.promise.utils
{
	import com.promise.events.AppEvent;
	
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.http.HTTPService;
	
	public class ReadXMLUtil extends EventDispatcher
	{
		//默认加载xml路径
		private var xmlURL:String = "com/promise/config/ExampleConfig.xml";
		
		public function ReadXMLUtil(target:IEventDispatcher=null)
		{
			super(target);
		}
		
		//加载xml文件  
		public function xmlLoad(url:String = null):void{
			var configService:HTTPService = new HTTPService();
			if(url==null){
				configService.url = xmlURL;
			}else{
				configService.url = url;
			}
			configService.resultFormat = "e4x";
			configService.addEventListener(ResultEvent.RESULT, xmlLoadResult);
			configService.addEventListener(FaultEvent.FAULT, xmlLoadFault);	
			configService.send();
		}
		
		//读取配置文件成功
		public function xmlLoadResult(event:ResultEvent):void{
			var configXML:XML = event.result as XML;
			var appEvent:AppEvent = new AppEvent(AppEvent.LOADXML_SUCCESS,configXML);
			dispatchEvent(appEvent);
		}
		
		//读取配置文件错误
		public function xmlLoadFault(event:FaultEvent):void{
			var appEvent:AppEvent = new AppEvent(AppEvent.LOADXML_ERROR,"load xml error!");
			dispatchEvent(appEvent);
		}
	}
}
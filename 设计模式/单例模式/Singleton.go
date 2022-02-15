
type instance struct {
	name string
}

// 单例模式：“饿汉”模式

var lazyIns *instance

func GetLazyInstance() *instance {
	if lazyIns == nil {
		lazyIns = new(instance)
	}
	return lazyIns
}

/**
	"饿汉模式" 
 **/
var hunIns *instance

// 在包singleton包被导入后，init函数即刻自动运行
func init() {
	hunIns = new(instance)
	hunIns.name = "初始化单例模式：饿汉"
}
func GetHunInstance() *instance {
	return hunIns
}

// "双检查锁模式"
var mux sync.Mutex
func GetSafeInstance() *instance {
	if safeIns == nil {
		mux.Lock()
		defer mux.Unlock()
		if safeIns == nil {
			safeIns = new(instance)
		}
	}
	return safeIns
}

// sync包实现单例模式
var once sync.Once
var ins *instance

func GetInstance() *instance {
	once.Do(func() {
		ins = new(instance)
	})
	return ins
}


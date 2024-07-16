import UIKit
import SwiftUI
import ComposeApp

@main
struct IOSApp: App {   
    @UIApplicationDelegateAdaptor(AppDelegate.self)
           var appDelegate: AppDelegate

           @Environment(\.scenePhase)
           var scenePhase: ScenePhase

           var rootHolder: RootHolder { appDelegate.rootHolder }

           
           var body: some Scene {
               WindowGroup {
                   ContentView(component: rootHolder.root)
                       .onChange(of: scenePhase) { [self] newPhase in
                           switch newPhase {
                           case .background: LifecycleRegistryExtKt.stop(self.rootHolder.lifecycle)
                           case .inactive: LifecycleRegistryExtKt.pause(rootHolder.lifecycle)
                           case .active: LifecycleRegistryExtKt.resume(self.rootHolder.lifecycle)
                           @unknown default: break
                           }
                       }
               }
           }
}

class RootHolder : ObservableObject {
    let lifecycle: LifecycleRegistry
    let root: RootComponent

    init() {
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()

        root = UIControllerKt.RootComponent(lifecycleRegistry: lifecycle)

        LifecycleRegistryExtKt.create(lifecycle)
    }

    deinit {
        // Destroy the root component before it is deallocated
        LifecycleRegistryExtKt.destroy(lifecycle)
    }
}


struct ComposeView: UIViewControllerRepresentable {
        
    private var component : RootComponent
    
    init(component : RootComponent){
        self.component = component
    }
    
    func makeUIViewController(context: Context) -> UIViewController {
        UIControllerKt.MainViewController(component : component)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    
    private var component : RootComponent
    
    init(component : RootComponent){
        self.component = component
    }
    
    var body: some View {
        ComposeView(component: component)
            .ignoresSafeArea(edges: .all)
    }
}

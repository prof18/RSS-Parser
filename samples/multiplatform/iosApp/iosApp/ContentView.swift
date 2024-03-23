import SwiftUI
import shared

struct ContentView: View {
    
    var body: some View {
           NavigationStack {
               MainScreen()
               .navigationDestination(for: Route.self) { route in
                   switch route {
                   case .checker:
                       CheckerScreen()
                   }
               }
           }
       }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

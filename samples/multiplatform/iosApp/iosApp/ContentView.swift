import SwiftUI
import shared

struct ContentView: View {
    
    @StateObject var viewModel = MainViewModel()
    
    var body: some View {
        NavigationStack {
            MainScreen(viewModel: viewModel)
                .toolbar {
                    ToolbarItem {
                        Text(viewModel.uiState.feed?.title ?? "")
                            .font(.title)
                    }
                    
                    ToolbarItem(placement: .primaryAction) {
                        NavigationLink(value: Route.checker) {
                            Text("Feed checker")
                        }
                    }
                }
                .navigationDestination(for: Route.self) { route in
                    switch route {
                    case .checker:
                        CheckerScreen()
                    }
                }
        }.navigationTitle(viewModel.uiState.feed?.title ?? "")
    }
}

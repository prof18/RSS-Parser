import SwiftUI
import shared

struct ContentView: View {
    
    @StateObject var viewModel = MainViewModel()
    
    var body: some View {
        
        NavigationView {
        
            VStack(alignment: .leading) {
                if viewModel.uiState.isLoading {
                    LoadingView()
                } else if let error = viewModel.uiState.error {
                    ErrorView(
                        errorMessage: error,
                        onRetryClick: {
                            viewModel.getFeed()
                        }
                    )
                } else if let feed = viewModel.uiState.feed {
                    
                    
                    List {
                        ForEach(feed.items, id: \.self) { feedItem in
                            
                            HStack {
                                
                                VStack(alignment: .leading) {
                                    
                                    Text(feedItem.title)
                                        .font(.title3)
                                    
                                    if let subtitle = feedItem.subtitle {
                                        
                                        Text(subtitle)
                                            .padding(.top, 2)
                                            .font(.body)
                                    }
                                    
                                    Text(feedItem.dateString)
                                        .padding(.top, 2)
                                        .font(.caption)
                                }
                                
                                if let imageUrl = feedItem.imageUrl {
                                    AsyncImage(
                                        url: URL(string: imageUrl),
                                        content: { image in
                                            image.resizable()
                                                .scaledToFill()
                                                .frame(width: 100, height: 100, alignment: .center)
                                                .clipped()
                                            
                                        },
                                        placeholder: {
                                            ProgressView()
                                                .frame(width: 200, height: 200, alignment: .center)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }.navigationTitle(viewModel.uiState.feed?.title ?? "")
        }.onAppear {
            viewModel.getFeed()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

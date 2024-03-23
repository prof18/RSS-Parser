//
//  HomeSreen.swift
//  iosApp
//
//  Created by Marco Gomiero on 14/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct MainScreen: View {
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
            }
            
        }
        .toolbar {
            
            ToolbarItem(placement: .navigationBarLeading) {
                Text(viewModel.uiState.feed?.title ?? "")
                    .font(.title)
            }
            
            ToolbarItem(placement: .primaryAction) {
                
                NavigationLink(value: Route.checker) {
                    Text("Feed checker")
                }
            }
        }.onAppear {
            viewModel.getFeed()
        }
    }
}

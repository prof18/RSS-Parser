//
//  MainViewModel.swift
//  iosApp
//
//  Created by Marco Gomiero on 19/03/23.
//  Copyright © 2023. All rights reserved.
//

import Foundation
import shared

class MainViewModel: ObservableObject {
    
    private let url = "https://www.androidauthority.com/feed"
    
    private let feedRepository = DI.shared.feedRepository
    
    @Published var uiState: UIState = UIState(
        isLoading: false,
        feed: nil,
        error: nil
    )
    
    func getFeed() {
        uiState = UIState(
            isLoading: true,
            feed: nil,
            error: nil
        )
        
        feedRepository.getFeed(url: url) { [self] feed, error in
            if error != nil {
                print("Something is wrong: \(String(describing: error))")
                DispatchQueue.main.async { [self] in
                    uiState = UIState(
                        isLoading: false,
                        feed: nil,
                        error: "Something went wrong"
                    )
                }
                
            }
            
            if let feed = feed {
                DispatchQueue.main.async { [self] in
                    uiState = UIState(
                        isLoading: false,
                        feed: feed,
                        error: nil
                    )
                }
            }
        }
    }
}

//
//  ErrorView.swift
//  iosApp
//
//  Created by Marco Gomiero on 19/03/23.
//  Copyright © 2023. All rights reserved.
//

import SwiftUI
import shared

struct ErrorView: View {
    
    let errorMessage: String
    let onRetryClick: (() -> Void)?
    
    var body: some View {
        VStack {
            Spacer()
            
            Text(errorMessage)
            
            if let onRetry = onRetryClick {
                Button(action: onRetry) {
                    Text("Retry")
                }
            }
            
            Spacer()
        }
    }
}

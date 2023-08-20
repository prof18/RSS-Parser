//
//  CheckerScreen.swift
//  iosApp
//
//  Created by Marco Gomiero on 14/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CheckerScreen: View {
    
    @State private var rssLink = ""
    @State private var parsingResult = ""
    
    var body: some View {
        VStack {
            TextField(
                "Rss link",
                text: $rssLink
            )
            .keyboardType(.webSearch)
            .textFieldStyle(RoundedBorderTextFieldStyle())
            .padding(16)

            Text(parsingResult)
                .padding(16)
        }
        .onChange(of: rssLink) { link in
            if URL(string: link) != nil {
                let feedRepository = DI.shared.feedRepository
                feedRepository.getFeed(url: link) { feed, error in
                    if let feed = feed {
                        parsingResult = feed.debugDescription
                    }
                    
                    if let error = error {
                        parsingResult = "\(error)"
                    }
                }
            }
        }
    }
}

struct CheckerScreen_Previews: PreviewProvider {
    static var previews: some View {
        CheckerScreen()
    }
}

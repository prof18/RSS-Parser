//
//  LoadingView.swift
//  iosApp
//
//  Created by Marco Gomiero on 19/03/23.
//  Copyright © 2023. All rights reserved.
//

import SwiftUI

struct LoadingView: View {
    var body: some View {
        VStack {
            Spacer()
            ProgressView()
            Spacer()
        }
    }
}

struct LoadingView_Previews: PreviewProvider {
    static var previews: some View {
        LoadingView()
    }
}

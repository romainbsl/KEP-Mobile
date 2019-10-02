//
//  ViewController.swift
//  kepios
//
//  Created by Romain Bsl on 30/09/2019.
//  Copyright © 2019 Romain Bsl. All rights reserved.
//

import UIKit
import kep_common

class ViewController: UIViewController, TalkView {

    private var presenter: TalkPresenter!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        initPresenter()
        // Do any additional setup after loading the view, typically from a nib.
//
//        let label = UILabel(frame: CGRect(x: 0, y: 0, width: 300, height: 21))
//        label.center = CGPoint(x: 160, y: 285)
//        label.textAlignment = .center
//        label.font = label.font.withSize(25)
//        label.text = "Hello from " + ActualKt.platformName() + "!"
//        view.addSubview(label)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        presenter.attachView(view: self)
        
        presenter.getTalkList()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        presenter.detachView()
    }
    
    private func initPresenter() {
        presenter = InjectorCommon.init().provideTalkPresenter()
    }
    
    func onSuccessGetTalkList(talkList: [TalkEntity]) {
        var talks = ""
        
        for talk in talkList {
            talks += " - " + talk.title + "\n"
        }
        
        let label = UILabel(frame: CGRect(x: 0, y: 0, width: 400, height: 650))
        label.numberOfLines = 0
        label.center = CGPoint(x: 200, y: 500)
        label.textAlignment = .center
        label.font = label.font.withSize(20)
        label.text = talks
        view.addSubview(label)
    }
}

